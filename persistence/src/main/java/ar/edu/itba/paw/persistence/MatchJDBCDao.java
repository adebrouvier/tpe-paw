package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.model.Match;
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
            new Match(rs.getLong("home_player_id"), rs.getLong("away_player_id"), rs.getInt("home_player_score"), rs.getInt("away_player_score"), rs.getLong("match_id"), rs.getInt("next_match_id"), rs.getLong("tournament_id"));

    @Autowired
    public MatchJDBCDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .usingColumns("match_id","tournament_id","next_match_id","home_player_id","away_player_id","home_player_score","away_player_score")
                .withTableName("match");
    }

    @Override
    public Match create(final int matchId, final int nextMatchId, final long tournamentId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("match_id", matchId);
        args.put("tournament_id", tournamentId);

        if (nextMatchId != 0)
            args.put("next_match_id", nextMatchId);
        else
            args.put("next_match_id", null);

        jdbcInsert.execute(args);
        return new Match(matchId, nextMatchId, tournamentId);
    }

    @Override
    public Match create(int matchId, int nextMatchId, long tournamentId, long homePlayerId, long awayPlayerId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("match_id", matchId);
        args.put("tournament_id", tournamentId);
        args.put("next_match_id", nextMatchId);
        args.put("home_player_id", homePlayerId);
        args.put("away_player_id", awayPlayerId);
        jdbcInsert.execute(args);
        return new Match(homePlayerId, awayPlayerId, matchId, nextMatchId, tournamentId);
    }

    @Override
    public Match findById(final int matchId, final long tournamentId) {
        List<Match> list = jdbcTemplate.query("SELECT * FROM match WHERE match_id = ? and tournament_id = ?", ROW_MAPPER, matchId, tournamentId);

        return list.get(0);
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
        jdbcTemplate.update("UPDATE match SET home_player_score = ?, away_player_score = ? WHERE match_id = ? and tournament_id = ?", homeScore, awayScore, matchId, tournamentId);
        return findById(matchId,tournamentId);
    }

    @Override
    public List<Match> getTournamentMatches(long tournamentId) {
        return jdbcTemplate.query("SELECT * FROM match"  +
                " WHERE tournament_id = ?", ROW_MAPPER, tournamentId);
    }

}
