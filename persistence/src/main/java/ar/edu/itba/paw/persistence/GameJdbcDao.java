package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.GameDao;
import ar.edu.itba.paw.model.Game;
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
public class GameJdbcDao implements GameDao{

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Game> ROW_MAPPER = (rs, rowNum) -> new Game(rs.getLong("game_id"), rs.getString("name"));

    @Autowired
    public GameJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("game")
                .usingGeneratedKeyColumns("game_id");
    }


    @Override
    public List<String> findGamesName() {
        List<String> list = jdbcTemplate.queryForList("SELECT name FROM game WHERE NOT user_generated ",String.class);
        if(list == null) {
            return null;
        }

        return list;
    }

    @Override
    public Game findById(long id) {
        List<Game> g = jdbcTemplate.query("SELECT * FROM game WHERE game_id = ?", ROW_MAPPER, id);
        if (g == null) {
            return null;
        } else if (g.isEmpty()) {
            return null;
        }
        return g.get(0);
    }

    @Override
    public Game findByName(String name) {
        List<Game> g = jdbcTemplate.query("SELECT * FROM game WHERE name LIKE ?", ROW_MAPPER, name);
        if(g == null) {
            return null;
        }else if(g.isEmpty()){
            return null;
        }


        return g.get(0);
    }

    @Override
    public Game create(String name, boolean userGenerated) {

        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("user_generated", userGenerated);
        final Number gameId = jdbcInsert.executeAndReturnKey(args);
        return new Game(gameId.longValue(), name);
    }

}