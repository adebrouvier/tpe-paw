package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.GameUrlImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Repository
public class GameUrlImageJdbcDao implements GameUrlImageDao{

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;


    @Autowired
    public GameUrlImageJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("game_url_image")
                .usingColumns("game_id", "url_image");
    }
    @Override
    public void create(long gameId, String url) {
        final Map<String, Object> args = new HashMap<>();
        args.put("game_id", gameId);
        args.put("url_image", url);
        jdbcInsert.execute(args);
    }

    @Override
    public String findById(long gameId) {
        String url = jdbcTemplate.queryForObject("SELECT url_image FROM game_url_image WHERE id = ?", String.class, gameId);
        return url;
    }
}
