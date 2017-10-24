package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.DuplicateUsernameException;
import ar.edu.itba.paw.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class RankingJdbcDaoTest {
    @Autowired
    private DataSource ds;

    @Autowired
    private MatchJDBCDao matchDao;
    @Autowired
    private PlayerJdbcDao playerJdbcDao;
    @Autowired
    private TournamentJdbcDao tournamentJdbcDao;
    @Autowired
    private RankingJdbcDao rankingJdbcDao;
    @Autowired
    private UserJdbcDao userJdbcDao;
    @Autowired
    private GameJdbcDao gameJdbcDao;
    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "match");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "tournament");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "player");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ranking");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ranking_tournaments");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ranking_players");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "game");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
    }

    @Test
    public void test() throws DuplicateUsernameException {
        final User user = userJdbcDao.create("user", "useruser");
        final Player dummy = playerJdbcDao.create("Dummy");
        final Player player1 = playerJdbcDao.create("Alex");
        final Player player2 = playerJdbcDao.create("Alexis");
        final Game game = gameJdbcDao.create("Smash", true);
        final Tournament tourney = tournamentJdbcDao.create("Prueba", "Smash", 0);
        final Match match = matchDao.create(1,0,true, 0, 1, 2, 17);
        final Map<Tournament, Integer> criteria = new HashMap<>();
        criteria.put(tourney, 100);
        Ranking ranking = rankingJdbcDao.create("Ranking", criteria, "Smash", 1);
        assertNotNull(ranking);
        ranking = rankingJdbcDao.findById(0);
        assertEquals(0, ranking.getId());
        assertEquals("Ranking", ranking.getName());
    }
}
