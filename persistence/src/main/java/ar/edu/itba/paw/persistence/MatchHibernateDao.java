package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

import static ar.edu.itba.paw.interfaces.persistence.PlayerDao.EMPTY;

@Repository
public class MatchHibernateDao implements MatchDao{

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
        //TODO: call findById or pass instance?
        Player homePlayer = playerDao.findById(homePlayerId);
        Player awayPlayer = playerDao.findById(awayPlayerId);
        Match nextMach = findById(nextMatchId, tournamentId);
        Tournament tournament = tournamentDao.findById(tournamentId);

        if (awayPlayerId == -1) { /*Checks if there is a BYE*/
            homeScore = MatchService.BYE_WIN_SCORE;
            updateNextMatch(tournamentId, nextMatchId, homeScore,0, homePlayerId, awayPlayerId, isNextMatchHome);
        }

        final Match match = new Match(matchId, homePlayer, awayPlayer, homeScore, 0, nextMach, isNextMatchHome, tournament, standing);
        em.persist(match);

        if (awayPlayerId == -1) {
            updateStanding(homePlayerId, matchId, tournamentId);
        }

        return match;
    }

    @Override
    public Match findById(int id, long tournamentId) {
        final TypedQuery<Match> query = em.createQuery("from Match as m " +
                "where m.id = :id and m.tournament = :tournament", Match.class);
        query.setParameter("name", id);
        query.setParameter("tournament", tournamentId);
        final List<Match> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Match> getTournamentMatches(long tournamentId) {
        final TypedQuery<Match> query = em.createQuery("from Match as m " +
                "where m.tournament = :tournament", Match.class);
        query.setParameter("tournament", tournamentId);
        return query.getResultList();
    }

    @Override
    public Match updateScore(long tournamentId, int matchId, int homeScore, int awayScore) {

        Match match = findById(matchId, tournamentId);

        if (match == null){
            return null;
        }

        if(match.getHomePlayer().getId() == EMPTY || match.getAwayPlayer().getId() == EMPTY) {
            return null;
        }

        /* Update the score of the match */
        Query q = em.createQuery("UPDATE Match as m SET m.homePlayerScore = :homeScore, m.awayPlayerScore = :awayScore " +
                "WHERE m.id = :matchId AND m.tournament.id = :tournamentId");
        q.setParameter("homeScore", homeScore);
        q.setParameter("awayScore", awayScore);
        q.setParameter("matchId", matchId);
        q.setParameter("tournamentId", tournamentId);
        q.executeUpdate();

        if((homeScore > awayScore) && (match.getHomePlayerScore() < match.getAwayPlayerScore())) {
            updateRecursiveStanding(match.getAwayPlayer().getId(),matchId,match.getNextMatch().getId(),tournamentId);
            updateRecursive(tournamentId, match.getNextMatch().getId(), match.isNextMatchHome());
        } else if((homeScore < awayScore) && (match.getHomePlayerScore() > match.getAwayPlayerScore())) {
            updateRecursiveStanding(match.getHomePlayer().getId(),matchId,match.getNextMatch().getId(),tournamentId);
            updateRecursive(tournamentId, match.getNextMatch().getId(), match.isNextMatchHome());
        }

        if (homeScore > awayScore)
            updateStanding(match.getHomePlayer().getId(),matchId,tournamentId);
        else if (homeScore < awayScore){
            updateStanding(match.getAwayPlayer().getId(),matchId,tournamentId);
        }

        updateNextMatch(tournamentId,match.getNextMatch().getId(),homeScore,awayScore,match.getHomePlayer().getId(),match.getAwayPlayer().getId(),match.isNextMatchHome());

        return findById(matchId, tournamentId);
    }

    private void updateNextMatch(long tournamentId, long nextMatchId, int homeScore, int awayScore, long homePlayerId, long awayPlayerId, boolean nextMatchHome){

        if(nextMatchId == 0 || homeScore == awayScore) {
            return;
        }

        long winnerId = 0;

        if (homeScore > awayScore) {
            winnerId = homePlayerId;
        } else if (awayScore > homeScore){
            winnerId = awayPlayerId;
        }

        Query q;

        if (nextMatchHome) {
            //TODO: might not work
            q = em.createQuery("UPDATE Match as m SET m.homePlayer = :home_player WHERE m.id = :id AND m.tournament = :tournament");
            q.setParameter("home_player", winnerId);
            q.setParameter("id", nextMatchId);
            q.setParameter("tournament", tournamentId);
            q.executeUpdate();
        } else {
            q = em.createQuery("UPDATE Match as m SET m.awayPlayer = :away_player WHERE m.id = :id AND m.tournament = :tournament");
            q.setParameter("away_player", winnerId);
            q.setParameter("id", nextMatchId);
            q.setParameter("tournament", tournamentId);
            q.executeUpdate();
        }

    }

    private void updateRecursive(long tournamentId, long matchId, boolean nextMatchHome) {

        if(matchId == 0) {
            return;
        }

        Match match = findById((int)matchId, tournamentId);
        if(match != null) {
            if(match.getId() != 0) {
                if(nextMatchHome) {
                    if(match.getHomePlayer().getId() != 0) {
                        Query q = em.createQuery("UPDATE Match SET homePlayer = null," +
                                " homePlayerScore = :homeScore, awayPlayerScore = :awayScore " +
                                "WHERE id = :matchId AND tournament = :tournamentId");
                        q.setParameter("homeScore", 0);
                        q.setParameter("awayScore", 0);
                        q.setParameter("matchId", matchId);
                        q.setParameter("tournamentId", tournamentId);
                        q.executeUpdate();
                        if(match.getHomePlayerScore() < match.getAwayPlayerScore()) {
                            updateRecursiveStanding(match.getAwayPlayer().getId(),matchId, match.getNextMatch().getId(),tournamentId);
                        }
                        updateRecursive(tournamentId, match.getNextMatch().getId(), match.isNextMatchHome());
                    }
                } else {
                    if(match.getAwayPlayer().getId() != 0) {
                        Query q = em.createQuery("UPDATE Match SET awayPlayer = null," +
                                " homePlayerScore = :homeScore, awayPlayerScore = :awayScore " +
                                "WHERE id = :matchId AND tournament = :tournamentId");
                        q.setParameter("homeScore", 0);
                        q.setParameter("awayScore", 0);
                        q.setParameter("matchId", matchId);
                        q.setParameter("tournamentId", tournamentId);
                        q.executeUpdate();
                        if(match.getHomePlayerScore() > match.getAwayPlayerScore()) {
                            updateRecursiveStanding(match.getHomePlayer().getId(),matchId, match.getNextMatch().getId(),tournamentId);
                        }
                        updateRecursive(tournamentId, match.getNextMatch().getId(), match.isNextMatchHome());
                    }
                }
            }

        }
    }

    private void updateStanding(long playerId, long matchId, long tournamentId) {
        final Match m = findById((int) matchId, tournamentId);
        Query q = em.createQuery("UPDATE Player as p SET p.standing = :standing " +
                "WHERE p.id = :playerId AND p.tournament = :tournamentId");
        q.setParameter("tournamentId", tournamentId);
        q.setParameter("playerId", playerId);
        q.setParameter("standing", m.getStanding());
        q.executeUpdate();
    }

    private void updateRecursiveStanding(long playerId, long matchId, long nextMatchId, long tournamentId) {
        final Match m = findById((int) matchId, tournamentId);
        Query q = em.createQuery("UPDATE Player as p SET p.standing = :standing " +
                "WHERE p.id = :playerId AND p.tournament = :tournamentId");
        q.setParameter("tournamentId", tournamentId);
        q.setParameter("playerId", playerId);

        if(nextMatchId == 0) {
            q.setParameter("standing", m.getStanding()*2);
        } else {
            q.setParameter("standing", (m.getStanding()*2)-1);
        }
        q.executeUpdate();
    }
}
