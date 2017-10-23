package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.RankingDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RankingJdbcDao implements RankingDao {

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    private final SimpleJdbcInsert tournamentToRankingInsert;

    private final SimpleJdbcInsert userToRankingInsert;

    private final static RowMapper<Ranking> RANKING_MAPPER = (rs, rowNum) -> new Ranking(rs.getInt("ranking_id"), rs.getString("name"), rs.getInt("game_id"));

    private final static RowMapper<UserScore> USER_RANKING_MAPPER = (rs, rowNum) -> new UserScore(rs.getLong("user_id"), rs.getInt("points"));

    private final static RowMapper<TournamentPoints> TOURNAMENT_MAPPER = (rs, rowNum) -> new TournamentPoints(rs.getLong("tournament_id"), rs.getInt("awarded_points"));

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GameJdbcDao gameDao;

    @Autowired
    public RankingJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ranking")
                .usingGeneratedKeyColumns("ranking_id");
        tournamentToRankingInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ranking_tournaments")
                .usingColumns("ranking_id", "tournament_id", "awarded_points");
        userToRankingInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ranking_players")
                .usingColumns("ranking_id", "user_id", "points");
    }

    @Override
    public Ranking findById(long rankingId) {
        final List<Ranking> list = jdbcTemplate.query("SELECT * FROM ranking WHERE ranking_id = ?",
                RANKING_MAPPER, rankingId);
        if (list.isEmpty()) {
            return null;
        }

        Ranking r = list.get(0);
        r.setUsers(getRankingUsers(rankingId));
        r.setTournaments(getRankingTournaments(rankingId));
        return r;
    }

    @Override
    public Ranking create(String name, Map<Tournament, Integer> tournaments, String game) {
        final Map<String, Object> args = new HashMap<>();

        long gameId = 0;
        final Game g = gameDao.findByName(game);
        if (g != null){
            gameId = g.getGameId();
            args.put("game_id", gameId);
        }
        args.put("name", name);
        final Number rankingId = jdbcInsert.executeAndReturnKey(args);
        return new Ranking(rankingId.longValue(), name, gameId);
    }

    @Override
    public List<Ranking> findByName(String name) {

        StringBuilder sb = new StringBuilder(name.toLowerCase());
        sb.insert(0, "%");
        sb.append("%");
        final List<Ranking> list = jdbcTemplate.query("SELECT * FROM ranking WHERE lower(name) LIKE ?",
                RANKING_MAPPER, sb.toString());
        if (list.isEmpty()) {
            return null;
        }

        return list;
    }

    @Override
    public List<String> findRankingNames(String query) {
        StringBuilder sb = new StringBuilder(query.toLowerCase());
        sb.insert(0, "%");
        sb.append("%");
        return jdbcTemplate.queryForList("SELECT name FROM ranking WHERE lower(name) LIKE ?", String.class, sb.toString());
    }

    @Override
    public void delete(long rankingId, long tournamentId) {
        int awardedPoints = jdbcTemplate.queryForObject("SELECT awarded_points FROM ranking_tournaments WHERE ranking_id = ? AND tournament_id = ?", Integer.class, rankingId, tournamentId);
        jdbcTemplate.update("DELETE FROM ranking_tournaments WHERE ranking_id = ? AND tournament_id = ?", rankingId, tournamentId);
        deleteUsersFromRanking(rankingId, tournamentId, awardedPoints);
    }

    @Override
    public Ranking addTournaments(long rankingId, Map<Tournament, Integer> tournaments) {
        Map<Tournament, Integer> filteredTournaments = new HashMap<>();
        boolean flag = true; //TODO think a better solution
        Ranking r = findById(rankingId);
        for (Tournament tournament : tournaments.keySet()) {
            if (r.getGameId() == tournament.getGameId()) {
                if (tournament.getStatus() == Tournament.Status.FINISHED) {
                    for(TournamentPoints tPoints: r.getTournaments()){
                        if(tPoints.getTournamentId() == tournament.getId()){
                            flag = false;
                        }
                    }
                    if(flag){
                        filteredTournaments.put(tournament, tournaments.get(tournament));
                    }
                }
            }
            flag = true;
        }

        addTournamentToRanking(rankingId, filteredTournaments);
        addUsersToRanking(rankingId, filteredTournaments);
        return findById(rankingId);

    }

    @Override
    public List<Ranking> findFeaturedRankings(int featured) {
        final List<Ranking> list = jdbcTemplate.query("SELECT * FROM ranking ORDER BY ranking_id DESC LIMIT ?", RANKING_MAPPER, featured);

        if (list.isEmpty()) {
            return null;
        }

        for (Ranking r : list) {
            r.setGame(gameDao.findById(r.getGameId()));
        }

        return list;
    }

    private void deleteUsersFromRanking(long rankingId, long tournamentId, int awardedPoints) {
        Tournament t = tournamentDao.findById(tournamentId);
        int standing, score, existingScore;
        long userId;
        for (Player player : t.getPlayers()) {
            userId = player.getUserId();
            if (userId != 0) {
                standing = jdbcTemplate.queryForObject("SELECT standing FROM participates_in WHERE player_id = ? AND tournament_id = ?", Integer.class, player.getId(), tournamentId);
                score = stadingHandler(standing, awardedPoints);
                existingScore = jdbcTemplate.queryForObject("SELECT points FROM ranking_players WHERE ranking_id = ? AND user_id = ?", Integer.class, rankingId, userId);
                if (score == existingScore) {
                    jdbcTemplate.update("DELETE FROM ranking_players WHERE ranking_id = ? AND user_id = ?", rankingId, userId);
                } else {
                    jdbcTemplate.update("UPDATE ranking_players SET points = ? WHERE ranking_id = ? AND user_id = ?", existingScore - score, rankingId, userId);
                }
            }
        }
    }

    private List<UserScore> getRankingUsers(long rankingId) {
        List<UserScore> users;
        users = jdbcTemplate.query("SELECT user_id, points FROM ranking_players WHERE ranking_id = ? ORDER BY points DESC", USER_RANKING_MAPPER, rankingId);
        //TODO: Make a function getUserNameById
        for (UserScore user : users) {
            user.setUserName(userDao.findById(user.getUserId()).getName());
        }
        return users;

    }

    private List<TournamentPoints> getRankingTournaments(long rankingId) {
        List<TournamentPoints> tournamentsPoints = jdbcTemplate.query("SELECT tournament_id, awarded_points FROM ranking_tournaments WHERE ranking_id = ?", TOURNAMENT_MAPPER, rankingId);
        //TODO: Make a function getNameById()
        for (TournamentPoints tPoints : tournamentsPoints) {
            tPoints.setName(tournamentDao.findById(tPoints.getTournamentId()).getName());
        }
        return tournamentsPoints;

    }

    private void addTournamentToRanking(long rankingId, Map<Tournament, Integer> tournaments) {
        final Map<String, Object> args = new HashMap<>();
        for (Tournament tournament : tournaments.keySet()) {
            args.clear();
            args.put("ranking_id", rankingId);
            args.put("tournament_id", tournament.getId());
            args.put("awarded_points", tournaments.get(tournament));
            tournamentToRankingInsert.execute(args);
        }

    }

    /**
     * Adds the user scores on the listed tournaments, taking
     * into account their standings.
     *
     * @param rankingId   id of the ranking.
     * @param tournaments tournaments and their respective points.
     */
    protected void addUsersToRanking(long rankingId, Map<Tournament, Integer> tournaments) {
        final Map<String, Object> args = new HashMap<>();
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
                if (player.getId() != -1) {
                    userId = player.getUserId();
                    if (userId != 0) {
                        standing = jdbcTemplate.queryForObject("SELECT standing FROM participates_in WHERE player_id = ? AND tournament_id = ?", Integer.class, player.getId(), tournament.getId());
                        userScore = stadingHandler(standing, tournamentScore);

                        if (existingScores.containsKey(userId)) {
                            existingScores.put(userId,userScore + existingScores.get(userId));
                        } else {
                            newUserScores.put(userId, userScore);
                        }
                    }
                }
            }
        }
        for(Map.Entry<Long,Integer> entry : existingScores.entrySet()){
            jdbcTemplate.update("UPDATE ranking_players SET points = ? WHERE ranking_id = ? AND user_id = ?",entry.getValue(), rankingId,entry.getKey());
        }
        for (Map.Entry<Long, Integer> entry : newUserScores.entrySet()) {
            args.clear();
            args.put("ranking_id", rankingId);
            args.put("user_id", entry.getKey());
            args.put("points", entry.getValue());
            userToRankingInsert.execute(args);
        }
    }

    /**
     * Decides the points criteria.
     *
     * @param standing        players standing.
     * @param tournamentScore tournaments awarded points.
     * @return amount of points awarded to the player.
     */
    private int stadingHandler(int standing, int tournamentScore) {
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
}