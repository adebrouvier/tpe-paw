package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
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
                .usingColumns("player_id","tournament_id");
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

    @Override
    public Player create(String name) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        final Number playerId = playerjdbcInsert.executeAndReturnKey(args);
        return new Player(name,playerId.longValue());
    }

    @Override
    public boolean addToTournament(long playerId, long tournamentId) {

        final Map<String, Object> args = new HashMap<>();
        args.put("player_id", playerId);
        args.put("tournament_id", tournamentId);

        int numberOfRowsInserted = participatesInjdbcInsert.execute(args);

        return numberOfRowsInserted == 1;
    }

    @Override
    public List<Player> getTournamentPlayers(long tournamentId) {
        return jdbcTemplate.query("SELECT * FROM player NATURAL JOIN participates_in" +
                        " WHERE tournament_id = ?", ROW_MAPPER, tournamentId);
    }
}
