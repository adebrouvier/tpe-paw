package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
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
public class PlayerJdbcDao implements PlayerDao{

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert playerjdbcInsert;

    private final SimpleJdbcInsert participatesInjdbcInsert;

    private final static RowMapper<Player> ROW_MAPPER = (rs, rowNum) -> new Player(rs.getString("name"), rs.getLong("player_id"));

    @Autowired
    public PlayerJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        playerjdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("player")
                .usingGeneratedKeyColumns("player_id");
        participatesInjdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("participates_in")
                .usingColumns("player_id","tournament_id","seed");
    }

    @Override
    public Player findById(long id) {
        final List<Player> list = jdbcTemplate.query("SELECT * FROM player WHERE player_id = ?",
                ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private final static RowMapper<Long> LONG_MAPPER = (rs, rowNum) -> (rs.getLong("player_id"));

    @Override
    public long findBySeed(int seed, long tournamentId) {

        //TODO Select the best option

        /*final List<Player> list = jdbcTemplate.query(
                "SELECT * FROM participates_in WHERE player_id = (SELECT player_id FROM participates_in WHERE seed = ?)",ROW_MAPPER, seed);
                */

        final List<Long> list = jdbcTemplate.query("SELECT * FROM participates_in WHERE seed = ? AND tournament_id = ?",LONG_MAPPER,seed,tournamentId);

        if (list.isEmpty()) {
            return 0;
        }
        return list.get(0);
    }

    @Override
    public Player create(String name) {
        final Map<String, Object> args = new HashMap<>();

        if(name.length() > 25) { //TODO ver que no rompe nada
            return null;
        }

        args.put("name", name);
        final Number playerId = playerjdbcInsert.executeAndReturnKey(args);
        return new Player(name,playerId.longValue());
    }

    @Override
    public boolean addToTournament(long playerId, long tournamentId, int seed) {

        final Map<String, Object> args = new HashMap<>();
        args.put("player_id", playerId);
        args.put("tournament_id", tournamentId);
        args.put("seed",seed);

        int numberOfRowsInserted = participatesInjdbcInsert.execute(args);

        return numberOfRowsInserted == 1;
    }

    @Override
    public List<Player> getTournamentPlayers(long tournamentId) {
        return jdbcTemplate.query("SELECT * FROM player NATURAL JOIN participates_in" +
                        " WHERE tournament_id = ?", ROW_MAPPER, tournamentId);
    }
}
