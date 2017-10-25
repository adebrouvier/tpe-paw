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

import static junit.framework.TestCase.assertNull;
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
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "participates_in");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ranking_tournaments");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ranking_players");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ranking");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "match");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "tournament");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "game");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "player");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        jdbcTemplate.execute("INSERT INTO users VALUES (1, 'Pibe', 'contraseña')");
        jdbcTemplate.execute("INSERT INTO users VALUES (2, 'Pibe2', 'contraseña')");
        jdbcTemplate.execute("INSERT INTO player VALUES (1, 'Jugador1', 1)");
        jdbcTemplate.execute("INSERT INTO player VALUES (2,  'Jugador2', 2)");
        jdbcTemplate.execute("INSERT INTO game VALUES (1, 'Smash', true)");
        jdbcTemplate.execute("INSERT INTO game VALUES (2, 'Smosh', true)");
        jdbcTemplate.execute("INSERT INTO tournament VALUES (1, 'NEW', 'Torneo', 1, 1)");
        jdbcTemplate.execute("INSERT INTO tournament VALUES (2, 'NEW', 'Torneo2', 2, 2)");
        jdbcTemplate.execute("INSERT INTO ranking VALUES (1, 'Ranking', 1, 1)");
        jdbcTemplate.execute("INSERT INTO ranking VALUES (2, 'Ranking2', 2, 1)");
        jdbcTemplate.execute("INSERT INTO ranking_tournaments VALUES (1,1,1000)");
        jdbcTemplate.execute("INSERT INTO ranking_tournaments VALUES (1,2,1000)");
        jdbcTemplate.execute("INSERT INTO ranking_tournaments VALUES (2,1,2000)");
        jdbcTemplate.execute("INSERT INTO ranking_players VALUES (1,1,800)");
        jdbcTemplate.execute("INSERT INTO ranking_players VALUES (1,2,400)");
        jdbcTemplate.execute("INSERT INTO ranking_players VALUES (2,1,1600)");
        jdbcTemplate.execute("INSERT INTO participates_in VALUES (1,1,1,3)");
        jdbcTemplate.execute("INSERT INTO participates_in VALUES (2,1,1,3)");
    }

    @Test
    public void testCreateRanking() throws DuplicateUsernameException {
        final Map<Tournament, Integer> criteria = new HashMap<>();
        criteria.put(tournamentJdbcDao.findById(1), 100);
        Ranking ranking = rankingJdbcDao.create("Ranking", criteria, "Smash", 1);
        assertNotNull(ranking);
    }

    @Test
    public void testFindRankingByIdSuccess() {
        assertNotNull(rankingJdbcDao.findById(1));
    }

    @Test
    public void testFindRankingByIdFailure() {
        assertNull(rankingJdbcDao.findById(123));
    }

    @Test
    public void testGetPlayers() {
        Ranking ranking = rankingJdbcDao.findById(1);
        assertEquals(2, ranking.getUsers().size());
    }

    @Test
    public void testGetPointsCorrectly() {
        assertEquals(800, rankingJdbcDao.findById(1).getUsers().get(0).getPoints());
    }

    @Test
    public void testGetTournamentPointValue() {
        assertEquals(1000, rankingJdbcDao.findById(1).getTournaments().get(0).getAwardedPoints());
    }

    @Test
    public void testFindRankingsByName() {
        assertEquals(2, rankingJdbcDao.findRankingNames("Ran").size());
    }

    @Test
    public void testFindFeaturedRanking() {
        assertEquals(2, rankingJdbcDao.findFeaturedRankings(10).size());
    }
}
