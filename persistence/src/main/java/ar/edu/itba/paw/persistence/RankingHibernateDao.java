package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.GameDao;
import ar.edu.itba.paw.interfaces.persistence.RankingDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RankingHibernateDao implements RankingDao{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserDao userDao;

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private GameDao gameDao;

    @Override
    public Ranking findById(long rankingId) {
        Ranking ranking = em.find(Ranking.class, rankingId);
        if(ranking == null) {
            return null;
        }

        ranking.setUsers(getRankingUsers(rankingId));
        ranking.setTournaments(getRankingTournaments(rankingId));
        return ranking;
    }

    private List<UserScore> getRankingUsers(long rankingId) {

        TypedQuery<UserScore> users =  em.createQuery("SELECT u.name, r.user_id, r.points, r.ranking_id FROM ranking_players AS r NATURAL JOIN users AS u WHERE r.ranking_id = :rankingId ORDER BY r.points DESC", UserScore.class)
                .setParameter("rankingId",rankingId);
        return users.getResultList();

    }

    private List<TournamentPoints> getRankingTournaments(long rankingId) {
        TypedQuery<TournamentPoints> tournamentsPoints = em.createQuery("SELECT t.name, r.tournament_id, r.awarded_points, r.ranking_id FROM ranking_tournaments AS r NATURAL JOIN tournament AS t WHERE r.ranking_id = :rankingId", TournamentPoints.class)
                .setParameter("rankingId", rankingId);
        return tournamentsPoints.getResultList();

    }

    @Override
    public Ranking create(String name, Map<Tournament, Integer> tournaments, String game, long userId) {

        final Game g = gameDao.findByName(game);
        final User u = userDao.findById(userId);
        Ranking ranking = new Ranking(name, g, u);
        em.persist(ranking);
        em.flush();
        return ranking;
    }

    @Override
    public List<Ranking> findByName(String term) {
        StringBuilder sb = new StringBuilder(term.toLowerCase());
        sb.insert(0, "%");
        sb.append("%");
        final TypedQuery<Ranking> list = em.createQuery("FROM ranking WHERE lower(name) LIKE :query", Ranking.class).setParameter("query", sb.toString());
        List<Ranking> rankings = list.getResultList();

        if (rankings == null || rankings.isEmpty()) {
            return null;
        }

        return rankings;
    }

    @Override
    public List<String> findRankingNames(String query) {
        StringBuilder sb = new StringBuilder(query.toLowerCase());
        sb.insert(0, "%");
        sb.append("%");
        final TypedQuery<String> list = em.createQuery("SELECT name FROM ranking WHERE lower(name) LIKE :query", String.class)
                .setParameter("query", sb.toString());
        List<String> rankings = list.getResultList();

        if (rankings == null || rankings.isEmpty()) {
            return null;
        }

        return rankings;
    }

    @Override
    public void delete(long rankingId, long tournamentId) {
        int awardedPoints = em.createQuery("SELECT awarded_points FROM ranking_tournaments WHERE ranking_id = :rankingId AND tournament_id = :tournamentId", Integer.class)
                .setParameter("rankingId", rankingId)
                .setParameter("tournamentId",tournamentId)
                .getFirstResult();
        TournamentPoints tournamentPoints = new TournamentPoints(rankingId, tournamentId, awardedPoints);
        em.remove(tournamentPoints);
        //jdbcTemplate.update("DELETE FROM ranking_tournaments WHERE ranking_id = ? AND tournament_id = ?", rankingId, tournamentId);
        deleteUsersFromRanking(rankingId, tournamentId, awardedPoints);
    }

    private void deleteUsersFromRanking(long rankingId, long tournamentId, int awardedPoints) {
        Tournament t = tournamentDao.findById(tournamentId);
        int standing;
        int score;
        int existingScore;
        long userId;
        for (Player player : t.getPlayers()) {
            userId = player.getUser().getId();
            if (userId != 0) {
                standing = em.createQuery("SELECT standing FROM player WHERE player_id = :playerId AND tournament_id = :tournamentId", Integer.class)
                        .setParameter("playerId", player.getId())
                        .setParameter("tournamentId", tournamentId)
                        .getFirstResult();
                score = standingHandler(standing, awardedPoints);
                existingScore = em.createQuery("SELECT points FROM ranking_players WHERE ranking_id = :rankingId AND user_id = :userId", Integer.class)
                        .setParameter("rankingId", rankingId)
                        .setParameter("userId", userId)
                        .getFirstResult();
                if (score == existingScore) {
                    UserScore userScore = new UserScore(rankingId, userId, score);
                    em.remove(userScore);
                    //jdbcTemplate.update("DELETE FROM ranking_players WHERE ranking_id = ? AND user_id = ?", rankingId, userId);
                } else {
                    int newScore = (existingScore - score);
                    UserScore userScore = new UserScore(rankingId, userId, newScore);
                    em.merge(userScore);
                    //jdbcTemplate.update("UPDATE ranking_players SET points = ? WHERE ranking_id = ? AND user_id = ?", existingScore - score, rankingId, userId);
                }
            }
        }
    }

    /**
     * Decides the points criteria.
     *
     * @param standing        players standing.
     * @param tournamentScore tournaments awarded points.
     * @return amount of points awarded to the player.
     */
    private int standingHandler(int standing, int tournamentScore) {
        int playerScore;
        switch (standing) {
            case RankingDao.FIRST:
                playerScore = (int) (tournamentScore * RankingDao.FIRST_SCORE);
                return playerScore;
            case RankingDao.SECOND:
                playerScore = (int) (tournamentScore * RankingDao.SECOND_SCORE);
                return playerScore;
            case RankingDao.THIRD:
                playerScore = (int) (tournamentScore * RankingDao.THIRD_SCORE);
                return playerScore;
            case RankingDao.FOURTH:
                playerScore = (int) (tournamentScore * RankingDao.FOURTH_SCORE);
                return playerScore;
            case RankingDao.FIFTH:
                playerScore = (int) (tournamentScore * RankingDao.FIFTH_SCORE);
                return playerScore;
            default:
                playerScore = RankingDao.NO_SCORE;
                return playerScore;
        }
    }

    @Override
    public Ranking addTournaments(long rankingId, Map<Tournament, Integer> tournaments) {

        Map<Tournament, Integer> filteredTournaments = new HashMap<>();
        Ranking r = findById(rankingId);
        for (Tournament tournament : tournaments.keySet()) {
            if(checkTournamentValidForRanking(r, tournament)) {
                filteredTournaments.put(tournament, tournaments.get(tournament));
            }

        }

        addTournamentToRanking(rankingId, filteredTournaments);
        addUsersToRanking(rankingId, filteredTournaments);
        return findById(rankingId);
    }

    private boolean checkTournamentValidForRanking(Ranking ranking, Tournament tournament) {
        if (ranking.getGame().getId() == tournament.getGame().getId() && tournament.getStatus() == Tournament.Status.FINISHED ) {
            if(ranking.getTournaments().size() == 0) return true;
            boolean alreadyAddedToRankingFlag = false;
            for(TournamentPoints tPoints: ranking.getTournaments()){
                if(tPoints.getTournamentId() == tournament.getId()) alreadyAddedToRankingFlag = true;
            }
            return !alreadyAddedToRankingFlag;
        }
        else {
            return false;
        }
    }

    private void addTournamentToRanking(long rankingId, Map<Tournament, Integer> tournaments) {
        for (Tournament tournament : tournaments.keySet()) {
            TournamentPoints tp = new TournamentPoints(rankingId, tournament.getId(), tournaments.get(tournament));
            em.persist(tp);
        }

    }

    /**
     * Adds the user scores on the listed tournaments, taking
     * into account their standings.
     *
     * @param rankingId   id of the ranking.
     * @param tournaments tournaments and their respective points.
     */
    private void addUsersToRanking(long rankingId, Map<Tournament, Integer> tournaments) {
        Map<Long, Integer> existingScores = new HashMap<>();
        Map<Long, Integer> newUserScores = new HashMap<>();
        Ranking r = findById(rankingId);
        for(UserScore tempScores:r.getUsers()){
            existingScores.put(tempScores.getUserId(),tempScores.getPoints());
        }
        int standing, userScore, tournamentScore;
        Long userId;
        for (Tournament tournament : tournaments.keySet()) {
            tournamentScore = tournaments.get(tournament);
            for (Player player : tournament.getPlayers()) {
                if (player.hasUser()) {
                    userId = player.getUser().getId();
                    if (userId != 0) {
                        standing = em.createQuery("SELECT standing FROM player WHERE player_id = :playerId AND tournament_id = :tournamentId", Integer.class)
                                .setParameter("playerId", player.getId())
                                .setParameter("tournamentId", tournament.getId())
                                .getFirstResult();
                        userScore = standingHandler(standing, tournamentScore);
                        if (existingScores.containsKey(userId)) { // Is user already in the ranking
                            existingScores.put(userId,userScore + existingScores.get(userId));
                        } else {
                            newUserScores.put(userId, userScore);
                        }
                    }
                }
            }
        }
        addPointsToExistingUsers(existingScores, rankingId);
        addNewUsersToRanking(newUserScores,rankingId);
    }

    private void addPointsToExistingUsers(Map<Long, Integer> points, long rankingId) {
        for(Map.Entry<Long,Integer> entry : points.entrySet()){
            UserScore us = new UserScore(rankingId, entry.getKey(), entry.getValue());
            em.merge(us);
            //jdbcTemplate.update("UPDATE ranking_players SET points = ? WHERE ranking_id = ? AND user_id = ?",entry.getValue(), rankingId,entry.getKey());
        }
    }

    private void addNewUsersToRanking(Map<Long, Integer> newUsers, long rankingId) {
        for (Map.Entry<Long, Integer> entry : newUsers.entrySet()) {
            UserScore us = new UserScore(rankingId, entry.getKey(), entry.getValue());
            em.persist(us);
        }
    }

    @Override
    public List<Ranking> findFeaturedRankings(int featured) {

        final List<Ranking> list = em.createQuery("FROM ranking ORDER BY ranking_id DESC LIMIT :featured", Ranking.class)
                .setParameter("featured", featured)
                .getResultList();

        if (list.isEmpty()) {
            return null;
        }

        for (Ranking r : list) {
            r.setGame(gameDao.findById(r.getGame().getId()));
        }

        return list;
    }
}
