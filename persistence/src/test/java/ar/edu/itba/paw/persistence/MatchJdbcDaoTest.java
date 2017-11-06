package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.interfaces.persistence.DuplicateUsernameException;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import jdk.nashorn.internal.scripts.JD;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class MatchJdbcDaoTest {
/*
    @Autowired
    private DataSource ds;

    @Autowired
    private MatchJDBCDao matchDao;
    @Autowired
    private PlayerJdbcDao playerJdbcDao;
    @Autowired
    private TournamentJdbcDao tournamentJdbcDao;
    @Autowired
    private UserJdbcDao userJdbcDao;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "participates_in");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ranking_tournaments");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ranking_players");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "ranking");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "match");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "tournament");;
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
        jdbcTemplate.execute("INSERT INTO match VALUES (3,1,1,2,0,0,1,null,TRUE)");
        jdbcTemplate.execute("INSERT INTO match VALUES (1,1,1,2,0,0,3,3,TRUE)");
        jdbcTemplate.execute("INSERT INTO match VALUES (2,1,1,2,0,0,3,3,TRUE)");
        jdbcTemplate.execute("INSERT INTO participates_in VALUES (1,1,1,3)");
        jdbcTemplate.execute("INSERT INTO participates_in VALUES (2,1,1,3)");
    }

    @Test
    public void testFindByIdSuccess() throws DuplicateUsernameException {
        assertNotNull(matchDao.findById(1,1));
    }

    @Test
    public void testFindByIdFailure() {
        assertNull(matchDao.findById(4,1));
    }

    @Test
    public void testUpdateMatchAwayScore() {
        matchDao.updateScore(1,1,1,2);
        assertEquals(2, matchDao.findById(1,1).getAwayPlayerScore());
    }

    @Test
    public void testUpdateMatchHomeScore() {
        matchDao.updateScore(1,1,1,2);
        assertEquals(1, matchDao.findById(1,1).getHomePlayerScore());
    }

    @Test
    public void testUpdatePlayer() {
        matchDao.updateStanding(1,3,1);
    }

    @Test
    public void testGetTournamentMatches() {
        assertEquals(3, matchDao.getTournamentMatches(1).size());
    }

    @Test
    public void testGetStanding() {
        assertEquals(1, matchDao.findById(3,1).getStanding());
    }
*/
}
