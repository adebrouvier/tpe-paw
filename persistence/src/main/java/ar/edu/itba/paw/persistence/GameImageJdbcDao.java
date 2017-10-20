package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.GameImageDao;
import ar.edu.itba.paw.model.GameImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class GameImageJdbcDao implements GameImageDao {

    private JdbcTemplate jdbcTemplate;

    private final static RowMapper<GameImage> ROW_MAPPER = (rs, rowNum) -> new GameImage(rs.getLong("game_id"), rs.getBytes("image"));

    @Autowired
    public GameImageJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);

    }

    @Override
    public GameImage findById(long id) {
        List<GameImage> list = jdbcTemplate.query("SELECT * FROM game_image WHERE game_id = ?",ROW_MAPPER, id);
        if(list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }
}
