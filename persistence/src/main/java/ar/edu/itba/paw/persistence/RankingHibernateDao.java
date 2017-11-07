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
        return em.find(Ranking.class, rankingId);
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
        final TypedQuery<Ranking> list = em.createQuery("FROM Ranking WHERE lower(name) LIKE :query", Ranking.class).setParameter("query", sb.toString());
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
        final TypedQuery<String> list = em.createQuery("SELECT name FROM Ranking WHERE lower(name) LIKE :query", String.class)
                .setParameter("query", sb.toString());
        List<String> rankings = list.getResultList();

        if (rankings == null || rankings.isEmpty()) {
            return null;
        }

        return rankings;
    }

    @Override
    public void delete(long rankingId, long tournamentId) {
        final Tournament tournament = tournamentDao.findById(tournamentId);
        final Ranking ranking = findById(rankingId);
        int awardedPoints = em.createQuery("SELECT awardedPoints FROM TournamentPoints WHERE ranking.id = :rankingId AND tournament.id = :tournamentId", Integer.class)
                .setParameter("rankingId", rankingId)
                .setParameter("tournamentId",tournamentId)
                .getFirstResult();
        TournamentPoints tournamentPoints = new TournamentPoints(ranking, tournament, awardedPoints);
        em.remove(tournamentPoints);
        deleteUsersFromRanking(ranking, tournament, awardedPoints);
    }

    private void deleteUsersFromRanking(Ranking ranking, Tournament tournament, int awardedPoints) {
        int standing;
        int score;
        int existingScore;
        User user;
        for (Player player : tournament.getPlayers()) {
            user = player.getUser();
            if (user.getId() != 0) {
                standing = em.createQuery("SELECT standing FROM Player WHERE id = :playerId AND tournament = :tournamentId", Integer.class)
                        .setParameter("playerId", player.getId())
                        .setParameter("tournamentId", tournament.getId())
                        .getFirstResult();
                score = standingHandler(standing, awardedPoints);
                existingScore = em.createQuery("SELECT points FROM UserScore WHERE ranking.id = :rankingId AND user.id = :userId", Integer.class)
                        .setParameter("rankingId", ranking.getId())
                        .setParameter("userId", user.getId())
                        .getFirstResult();
                if (score == existingScore) {
                    UserScore userScore = new UserScore(ranking, user, score);
                    em.remove(userScore);
                    //jdbcTemplate.update("DELETE FROM ranking_players WHERE ranking_id = ? AND user_id = ?", rankingId, userId);
                } else {
                    int newScore = (existingScore - score);
                    UserScore userScore = new UserScore(ranking, user, newScore);
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

        addTournamentToRanking(r, filteredTournaments);
        addUsersToRanking(r, filteredTournaments);
        return findById(rankingId);
    }

    private boolean checkTournamentValidForRanking(Ranking ranking, Tournament tournament) {
        if (ranking.getGame().getId() == tournament.getGame().getId() && tournament.getStatus() == Tournament.Status.FINISHED ) {
            if(ranking.getTournaments().size() == 0) return true;
            boolean alreadyAddedToRankingFlag = false;
            for(TournamentPoints tPoints: ranking.getTournaments()){
                if(tPoints.getTournament().getId() == tournament.getId()) alreadyAddedToRankingFlag = true;
            }
            return !alreadyAddedToRankingFlag;
        }
        else {
            return false;
        }
    }

    private void addTournamentToRanking(Ranking ranking, Map<Tournament, Integer> tournaments) {
        for (Tournament tournament : tournaments.keySet()) {
            TournamentPoints tp = new TournamentPoints(ranking, tournament, tournaments.get(tournament));
            em.persist(tp);
        }

    }

    /**
     * Adds the user scores on the listed tournaments, taking
     * into account their standings.
     *
     * @param ranking   id of the ranking.
     * @param tournaments tournaments and their respective points.
     */
    private void addUsersToRanking(Ranking ranking, Map<Tournament, Integer> tournaments) {
        Map<User, Integer> existingScores = new HashMap<>();
        Map<User, Integer> newUserScores = new HashMap<>();
        for(UserScore tempScores:ranking.getUsers()){
            existingScores.put(tempScores.getUser(),tempScores.getPoints());
        }
        int standing, userScore, tournamentScore;
        User user;
        for (Tournament tournament : tournaments.keySet()) {
            tournamentScore = tournaments.get(tournament);
            for (Player player : tournament.getPlayers()) {
                if (player.hasUser()) {
                    user = player.getUser();
                    if (user.getId() != 0) {
                        standing = em.createQuery("SELECT standing FROM Player WHERE id = :playerId AND tournament = :tournamentId", Integer.class)
                                .setParameter("playerId", player.getId())
                                .setParameter("tournamentId", tournament.getId())
                                .getFirstResult();
                        userScore = standingHandler(standing, tournamentScore);
                        if (existingScores.containsKey(user)) { // Is user already in the ranking
                            existingScores.put(user,userScore + existingScores.get(user));
                        } else {
                            newUserScores.put(user, userScore);
                        }
                    }
                }
            }
        }
        addPointsToExistingUsers(existingScores, ranking);
        addNewUsersToRanking(newUserScores,ranking);
    }

    private void addPointsToExistingUsers(Map<User, Integer> points, Ranking ranking) {
        for(Map.Entry<User,Integer> entry : points.entrySet()){
            UserScore us = new UserScore(ranking, entry.getKey(), entry.getValue());
            em.merge(us);
            //jdbcTemplate.update("UPDATE ranking_players SET points = ? WHERE ranking_id = ? AND user_id = ?",entry.getValue(), rankingId,entry.getKey());
        }
    }

    private void addNewUsersToRanking(Map<User, Integer> newUsers, Ranking ranking) {
        for (Map.Entry<User, Integer> entry : newUsers.entrySet()) {
            UserScore us = new UserScore(ranking, entry.getKey(), entry.getValue());
            em.persist(us);
        }
    }

    @Override
    public List<Ranking> findFeaturedRankings(int featured) {

        final List<Ranking> list = em.createQuery("FROM Ranking ORDER BY id DESC", Ranking.class)
                .setMaxResults(featured)
                .getResultList();

        if (list.isEmpty()) {
            return null;
        }

        return list;
    }
}