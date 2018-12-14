package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class MatchHibernateDao implements MatchDao {

  @PersistenceContext
  private EntityManager em;

  @Autowired
  private TournamentDao tournamentDao;

  @Autowired
  private PlayerDao playerDao;

  @Override
  public Match createEmpty(int matchId, int nextMatchId, boolean isNextMatchHome, long tournamentId, int standing) {
    Match nextMach = findById(nextMatchId, tournamentId);
    Tournament tournament = tournamentDao.findById(tournamentId);
    final Match match = new Match(matchId, nextMach, isNextMatchHome, tournament, standing);
    em.persist(match);
    return match;
  }

  @Override
  public Match create(int matchId, int nextMatchId, boolean isNextMatchHome, long tournamentId, long homePlayerId, long awayPlayerId, int standing) {

    int homeScore = 0;
    Player homePlayer = playerDao.findById(homePlayerId);
    Player awayPlayer = playerDao.findById(awayPlayerId);
    Match nextMach = findById(nextMatchId, tournamentId);
    Tournament tournament = tournamentDao.findById(tournamentId);

    if (awayPlayerId == TournamentService.BYE_ID) { /*Checks if there is a BYE*/
      homeScore = MatchService.BYE_WIN_SCORE;
      updateNextMatch(tournamentId, nextMatchId, homeScore, 0, homePlayerId, awayPlayerId, isNextMatchHome);
    }

    final Match match = new Match(matchId, homePlayer, awayPlayer, homeScore, 0, nextMach, isNextMatchHome, tournament, standing);
    em.persist(match);

    if (awayPlayerId == TournamentService.BYE_ID) {
      updateStanding(homePlayer, matchId, tournamentId);
    }

    return match;
  }

  @Override
  public Match findById(int id, long tournamentId) {
    final TypedQuery<Match> query = em.createQuery("from Match as m " +
      "where m.id = :id and m.tournament.id = :tournament", Match.class);
    query.setParameter("id", id);
    query.setParameter("tournament", tournamentId);
    final List<Match> list = query.getResultList();
    return list.isEmpty() ? null : list.get(0);
  }

  @Override
  public List<Match> getTournamentMatches(long tournamentId) {
    final TypedQuery<Match> query = em.createQuery("from Match as m " +
      "where m.tournament.id = :tournament", Match.class);
    query.setParameter("tournament", tournamentId);
    return query.getResultList();
  }

  @Override
  @Transactional
  public void addVideoOnDemand(String link, long tournamentId, int matchId) {

    Match match = findById(matchId, tournamentId);

    match.setLinkToVideoOnDemand(addHttpsToUrl(link));
    em.merge(match);
  }

  @Override
  @Transactional
  public void addMap(String mapName, long tournamentId, int matchId) {
    Match match = findById(matchId, tournamentId);
    match.setMap(mapName);
    em.merge(match);
  }

  @Override
  @Transactional
  public void addHomePlayerCharacter(String characterName, long tournamentId, int matchId) {
    Match match = findById(matchId, tournamentId);
    match.setHomePlayerCharacter(characterName);
    em.merge(match);
  }

  @Override
  public void addAwayPlayerCharacter(String characterName, long tournamentId, int matchId) {
    Match match = findById(matchId, tournamentId);
    match.setAwayPlayerCharacter(characterName);
    em.merge(match);
  }

  @Override
  public Match updateScore(long tournamentId, int matchId, int homeScore, int awayScore) {

    Match match = findById(matchId, tournamentId);

    if (match == null) {
      return null;
    }


    if (match.getHomePlayer() == null || match.getAwayPlayer() == null) {
      return null;
    }

    int previousHomeScore;
    int previousAwayScore;
    if (match.getHomePlayerScore() == null) {
      previousHomeScore = 0;
    } else {
      previousHomeScore = match.getHomePlayerScore();
    }
    if (match.getAwayPlayerScore() == null) {
      previousAwayScore = 0;
    } else {
      previousAwayScore = match.getAwayPlayerScore();
    }

    /* Update the score of the match */
    match.setHomePlayerScore(homeScore);
    match.setAwayPlayerScore(awayScore);
    em.merge(match);

    if ((homeScore > awayScore) && (previousHomeScore < previousAwayScore)) {
      updateRecursiveStanding(match.getAwayPlayer().getId(), matchId, match.getNextMatch().getId(), tournamentId);
      updateRecursive(tournamentId, match.getNextMatch().getId(), match.isNextMatchHome());
    } else if ((homeScore < awayScore) && (previousHomeScore > previousAwayScore)) {
      updateRecursiveStanding(match.getHomePlayer().getId(), matchId, match.getNextMatch().getId(), tournamentId);
      updateRecursive(tournamentId, match.getNextMatch().getId(), match.isNextMatchHome());
    }

    if (homeScore > awayScore)
      updateStanding(match.getHomePlayer(), matchId, tournamentId);
    else if (homeScore < awayScore) {
      updateStanding(match.getAwayPlayer(), matchId, tournamentId);
    }

    if (match.getNextMatch() != null) {
      updateNextMatch(tournamentId, match.getNextMatch().getId(), homeScore, awayScore, match.getHomePlayer().getId(), match.getAwayPlayer().getId(), match.isNextMatchHome());
    }

    return findById(matchId, tournamentId);
  }

  private void updateNextMatch(long tournamentId, long nextMatchId, int homeScore, int awayScore, long homePlayerId, long awayPlayerId, boolean nextMatchHome) {

    if (nextMatchId == 0 || homeScore == awayScore) {
      return;
    }

    Player winner = null;

    if (homeScore > awayScore) {
      winner = playerDao.findById(homePlayerId);
    } else if (awayScore > homeScore) {
      winner = playerDao.findById(awayPlayerId);
    }

    Match nextMatch = findById((int) nextMatchId, tournamentId);

    if (nextMatchHome) {
      nextMatch.setHomePlayer(winner);
      em.merge(nextMatch);
    } else {
      nextMatch.setAwayPlayer(winner);
      em.merge(nextMatch);
    }

  }

  private void updateRecursive(long tournamentId, long matchId, boolean nextMatchHome) {

    if (matchId == 0) {
      return;
    }

    Match match = findById((int) matchId, tournamentId);
    int previousHomeScore;
    int previousAwayScore;
    if (match.getHomePlayerScore() == null) {
      previousHomeScore = 0;
    } else {
      previousHomeScore = match.getHomePlayerScore();
    }
    if (match.getAwayPlayerScore() == null) {
      previousAwayScore = 0;
    } else {
      previousAwayScore = match.getAwayPlayerScore();
    }
    if (match != null) {
      if (match.getId() != 0) {
        if (nextMatchHome) {
          if (match.getHomePlayer().getId() != 0) {
            match.setHomePlayer(null);
            match.setHomePlayerScore(0);
            match.setAwayPlayerScore(0);
            em.merge(match);
            em.flush();
            if (previousHomeScore < previousAwayScore) {
              updateRecursiveStanding(match.getAwayPlayer().getId(), matchId, match.getNextMatch().getId(), tournamentId);
            }
            if (match.getNextMatch() != null) {
              updateRecursive(tournamentId, match.getNextMatch().getId(), match.isNextMatchHome());
            }
          }
        } else {
          if (match.getAwayPlayer().getId() != 0) {
            match.setAwayPlayer(null);
            match.setHomePlayerScore(0);
            match.setAwayPlayerScore(0);
            em.merge(match);
            em.flush();
            if (previousHomeScore > previousAwayScore) {
              updateRecursiveStanding(match.getHomePlayer().getId(), matchId, match.getNextMatch().getId(), tournamentId);
            }
            if (match.getNextMatch() != null) {
              updateRecursive(tournamentId, match.getNextMatch().getId(), match.isNextMatchHome());
            }
          }
        }
      }

    }
  }

  private void updateStanding(Player player, long matchId, long tournamentId) {
    final Match m = findById((int) matchId, tournamentId);
    player.setStanding(m.getStanding());
    em.merge(player);
  }

  private void updateRecursiveStanding(long playerId, long matchId, long nextMatchId, long tournamentId) {
    final Match m = findById((int) matchId, tournamentId);
    Query q = em.createQuery("UPDATE Player as p SET p.standing = :standing " +
      "WHERE p.id = :playerId AND p.tournament.id = :tournamentId");
    q.setParameter("tournamentId", tournamentId);
    q.setParameter("playerId", playerId);

    if (nextMatchId == 0) {
      q.setParameter("standing", m.getStanding() * 2);
    } else {
      q.setParameter("standing", (m.getStanding() * 2) - 1);
    }
    q.executeUpdate();
  }

  private String addHttpsToUrl(String url) {
    if (url == null || url.isEmpty()) {
      return null;
    }
    char c = url.toLowerCase().charAt(0);
    if (c == 'y' || c == 't' || c == 'w') {
      return "https://" + url;
    }
    return url;
  }

  public EntityManager getEntityManager() {
    return em;
  }
}
