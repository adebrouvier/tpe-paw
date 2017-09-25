package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
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
public class MatchJDBCDao implements MatchDao {

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Match> ROW_MAPPER = (rs, rowNum) ->
            new Match(rs.getLong("home_player_id"), rs.getLong("away_player_id"), rs.getInt("home_player_score"), rs.getInt("away_player_score"), rs.getLong("match_id"), rs.getInt("next_match_id"), rs.getBoolean("next_match_home"), rs.getLong("tournament_id"));

    @Autowired
    public MatchJDBCDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .usingColumns("match_id", "tournament_id", "next_match_id", "home_player_id", "away_player_id", "home_player_score", "away_player_score", "next_match_home")
                .withTableName("match");
    }

    @Override
    public Match create(final int matchId, final int nextMatchId, final boolean isNextMatchHome, final long tournamentId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("match_id", matchId);
        args.put("tournament_id", tournamentId);

        if (nextMatchId != 0)
            args.put("next_match_id", nextMatchId);
        else
            args.put("next_match_id", null);

        args.put("next_match_home", isNextMatchHome);
        jdbcInsert.execute(args);
        return new Match(matchId, nextMatchId, isNextMatchHome, tournamentId);
    }

    @Override
    public Match create(int matchId, int nextMatchId, boolean isNextMatchHome, long tournamentId, long homePlayerId, long awayPlayerId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("match_id", matchId);
        args.put("tournament_id", tournamentId);
        args.put("next_match_id", nextMatchId);
        args.put("home_player_id", homePlayerId);
        args.put("away_player_id", awayPlayerId);
        args.put("next_match_home", isNextMatchHome);
        jdbcInsert.execute(args);
        return new Match(homePlayerId, awayPlayerId, matchId, nextMatchId, isNextMatchHome, tournamentId);
    }

    @Override
    public Match createWinnerMatch(int matchId, int nextMatchId, int loserMatchId, boolean isNextMatchHome, long tournamentId) {
        return null;
    }

    @Override
    public Match createWinnerMatch(int matchId, int nextMatchId, int loserMatchId, boolean isNextMatchHome, long tournamentId, long homePlayerId, long awayPlayerId) {
        return null;
    }

    @Override
    public Match createLoserMatch(int matchId, int nextMatchId, boolean isNextMatchHome, long tournamentId) {
        return null;
    }

    @Autowired
    private PlayerDao playerDao;

    @Override
    public Match findById(final int matchId, final long tournamentId) {
        List<Match> list = jdbcTemplate.query("SELECT * FROM match WHERE match_id = ? and tournament_id = ?", ROW_MAPPER, matchId, tournamentId);

        Match m = list.get(0);

        if (m != null) {
            Player homePlayer = playerDao.findById(m.getHomePlayerId());
            Player awayPlayer = playerDao.findById(m.getAwayPlayerId());
            m.setHomePlayer(homePlayer);
            m.setAwayPlayer(awayPlayer);
        }

        return m;
    }

    @Override
    public Match addPlayer(final long tournamentId, final int matchId, final long playerId, final int type) {
        List<Match> list;
        if (type == MatchDao.HOME) {
            list = jdbcTemplate.query("UPDATE match SET home_player_id = ? WHERE match_id = ? and tournament_id = ?", ROW_MAPPER, playerId, matchId, tournamentId);
        }
        if (type == MatchDao.AWAY) {
            list = jdbcTemplate.query("UPDATE match SET away_player_id = ? WHERE match_id = ? and tournament_id = ?", ROW_MAPPER, playerId, matchId, tournamentId);
        } else {
            return null;
        }
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Match updateScore(long tournamentId, int matchId, int homeScore, int awayScore) {
        jdbcTemplate.update("UPDATE match SET home_player_score = ?, away_player_score = ? WHERE match_id = ? AND tournament_id = ?", homeScore, awayScore, matchId, tournamentId);
        Match match = findById(matchId, tournamentId);
        long winnerId;

        if (match.getAwayPlayerId() == -1) { /*Checks if there is a BYE*/
            jdbcTemplate.update("UPDATE match SET home_player_score = 1, away_player_score = 0 WHERE match_id = ? AND tournament_id = ?", matchId, tournamentId);
            homeScore = 1;/*TODO: Magic number, maybe there is a cleaner way*/
            awayScore = 0;
        } else if (match.getHomePlayerId() == -1) { /*This should never happen*/
            jdbcTemplate.update("UPDATE match SET home_player_score = 1, away_player_score = 1 WHERE match_id = ?  AND tournament_id = ?", matchId,tournamentId);
            homeScore = 0;
            awayScore = 1;
        }

        if(homeScore == 0 && awayScore == 0){ /*Should this be checked before?*/
            return findById(matchId, tournamentId);
        }

        if (match.getNextMatchId() != 0) { /* If there is a next round match */
            if (homeScore > awayScore) {
                winnerId = match.getHomePlayerId();
            } else {
                winnerId = match.getAwayPlayerId();
            }
            if (match.isNextMatchHome()) {
                jdbcTemplate.update("UPDATE match SET home_player_id = ? WHERE match_id = ? AND tournament_id = ?", winnerId, match.getNextMatchId(), tournamentId);
            } else {
                jdbcTemplate.update("UPDATE match SET away_player_id = ? WHERE match_id = ? AND tournament_id = ?", winnerId, match.getNextMatchId(), tournamentId);
            }
        }
        return findById(matchId, tournamentId);
    }

    @Override
    public List<Match> getTournamentMatches(long tournamentId) {
        List<Match> matches = jdbcTemplate.query("SELECT * FROM match" +
                " WHERE tournament_id = ? ORDER BY match_id DESC", ROW_MAPPER, tournamentId);

        //TODO ask if is okay to do this
        for (Match m : matches) {
            Player homePlayer = playerDao.findById(m.getHomePlayerId());
            Player awayPlayer = playerDao.findById(m.getAwayPlayerId());
            m.setHomePlayer(homePlayer);
            m.setAwayPlayer(awayPlayer);
        }

        return matches;
    }

}
