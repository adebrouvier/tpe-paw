package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
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
            new Match(rs.getLong("local_player_id"), rs.getLong("visitor_player_id"), rs.getInt("local_player_score"), rs.getInt("visitor_player_score"), rs.getInt("match_id"), rs.getInt("next_match_id"), rs.getLong("tournament_id"));

    @Autowired
    public MatchJDBCDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("match");
    }

    @Override
    public Match create(final Integer matchId, final Integer nextMatchId, final long tournamentId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("match_id", matchId);
        args.put("tournamet_id", tournamentId);
        args.put("next_match_id", nextMatchId);
        jdbcInsert.executeAndReturnKey(args);
        return new Match(matchId, nextMatchId, tournamentId);
    }

    @Override
    public Match create(Integer matchId, Integer nextMatchId, long tournamentId, long localPlayerId, long visitorPlayerId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("match_id", matchId);
        args.put("tournamet_id", tournamentId);
        args.put("next_match_id", nextMatchId);
        args.put("local_player_id", localPlayerId);
        args.put("visitor_player_id", visitorPlayerId);
        jdbcInsert.executeAndReturnKey(args);
        return new Match(localPlayerId, visitorPlayerId, matchId, nextMatchId, tournamentId);    }


    @Override
    public Match findById(final Integer matchId, final long tournamentId) {
        List<Match> list = jdbcTemplate.query("SELECT * FROM match WHERE match_id = ? and tournament_id = ?", ROW_MAPPER, matchId, tournamentId);

        return list.get(0);
    }

    @Override
    public Match addPlayer(final long tournamentId, final long matchId, final long playerId, final Integer type) {
        List<Match> list;
        if(type.equals(MatchDao.LOCAL)) {
            list = jdbcTemplate.query("UPDATE match SET local_player_id = ? WHERE match_id = ? and tournament_id = ?", ROW_MAPPER, playerId, matchId, tournamentId);
        }
        if(type.equals(MatchDao.VISITOR)) {
            list = jdbcTemplate.query("UPDATE match SET visitor_player_id = ? WHERE match_id = ? and tournament_id = ?", ROW_MAPPER, playerId, matchId, tournamentId);
        } else {
            return null;
        }
        if(list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public Match updateScore(long tournamentId, long matchId, Integer localScore, Integer visitorScore) {
        List<Match> list = jdbcTemplate.query("UPDATE match SET local_player_score = ?, visitor_player_score = ? WHERE match_id = ? and tournament_id = ?", ROW_MAPPER, localScore, visitorScore, matchId, tournamentId);

        return list.get(0);
    }

}
