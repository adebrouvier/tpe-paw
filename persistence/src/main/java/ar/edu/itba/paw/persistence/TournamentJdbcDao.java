package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
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
public class TournamentJdbcDao implements TournamentDao {

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Tournament> ROW_MAPPER = (rs, rowNum) -> new Tournament(rs.getString("name"), rs.getInt("tournament_id"));

    @Autowired
    public TournamentJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tournament")
                .usingGeneratedKeyColumns("tournament_id");
    }

    @Autowired
    private PlayerDao playerDao;

    @Override
    public Tournament findById(long id) {
        final List<Tournament> list = jdbcTemplate.query("SELECT * FROM tournament WHERE tournament_id = ?",
                ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }

        Tournament t  = list.get(0);

        final List<Player> players  = playerDao.getTournamentPlayers(id);

        t.addPlayer(players);

        return t;
    }

    @Override
    public Tournament create(String name,int maxParticipants, int cantParticipants) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("maxParticipants", maxParticipants);
        args.put("cantParticipants", cantParticipants);
        final Number tournamentId = jdbcInsert.executeAndReturnKey(args);
        return new Tournament(name,tournamentId.longValue());
    }
}
