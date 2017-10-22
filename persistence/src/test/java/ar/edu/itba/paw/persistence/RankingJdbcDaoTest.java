package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
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
    }

    @Test
    public void test() {
        final Player dummy = playerJdbcDao.create("Dummy");
        final Player player1 = playerJdbcDao.create("Alex");
        final Player player2 = playerJdbcDao.create("Alexis");
        final Tournament tourney = tournamentJdbcDao.create("Prueba", "Smash");
        final Match match = matchDao.create(1,0,true, 0, 1, 2, 17);
        final Map<Tournament, Integer> criteria = new HashMap<>();
        criteria.put(tourney, 100);
        Ranking ranking = rankingJdbcDao.create("Ranking", criteria);
        assertNotNull(ranking);
        ranking = rankingJdbcDao.findById(0);
        assertEquals(0, ranking.getId());
        assertEquals("Ranking", ranking.getName());
    }
}
