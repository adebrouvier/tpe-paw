package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.GameDao;
import ar.edu.itba.paw.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

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
    public List<Game> getGames() {
        List<Game> list = jdbcTemplate.query("SELECT * FROM game",ROW_MAPPER);
        if(list == null) {
            return null;
        }

        return list;
    }

    @Override
    public Game findById(long id) {
        List<Game> g = jdbcTemplate.query("SELECT * FROM game WHERE game_id = ?", ROW_MAPPER, id);
        if(g == null) {
            return null;
        }
        return g.get(0);
    }

    @Override
    public Game findByName(String name) {
        List<Game> g = jdbcTemplate.query("SELECT * FROM game WHERE name LIKE ?", ROW_MAPPER, name);
        if(g == null) {
            return null;
        }
        return g.get(0);
    }

}
