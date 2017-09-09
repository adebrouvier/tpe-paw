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
            new Match(rs.getLong("local_player_id"), rs.getLong("visitor_player_id"), rs.getInt("local_player_score"), rs.getInt("visitor_player_score"), rs.getInt("name"), rs.getLong("match_id"), rs.getLong("tournament_id"));

    @Autowired
    public MatchJDBCDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("match")
                .usingGeneratedKeyColumns("match_id");
    }

    @Override
    public Match create(final Integer name, final long tournamentId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("tournamet_id", tournamentId);
        final Number matchId = jdbcInsert.executeAndReturnKey(args);
        return new Match(name, matchId.longValue(), tournamentId);
    }

    @Override
    public Match findByName(Integer name, long tournamentId) {
        List<Match> list = jdbcTemplate.query("SELECT * FROM match WHERE name = ? and tournament_id = ?", ROW_MAPPER, name, tournamentId);

        return null;
    }

    @Override
    public boolean addPlayer(final long tournamentId, final long matchId, final long playerId, final Integer type) {
        List<Match> list;
        if(type.equals(MatchDao.LOCAL)) {
            list = jdbcTemplate.query("UPDATE match SET local_player_id = ? WHERE name = ? and tournament_id = ?", ROW_MAPPER, playerId, matchId, tournamentId);
        }
        if(type.equals(MatchDao.VISITOR)) {
            list = jdbcTemplate.query("UPDATE match SET visitor_player_id = ? WHERE name = ? and tournament_id = ?", ROW_MAPPER, playerId, matchId, tournamentId);
        } else {
            return false;
        }
        if(list.isEmpty()) {
            return false;
        }
        return true;
    }

}
