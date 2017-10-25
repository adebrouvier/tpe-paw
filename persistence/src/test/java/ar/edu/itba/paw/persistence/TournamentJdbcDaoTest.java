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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class TournamentJdbcDaoTest {

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
    @Autowired
    private GameJdbcDao gameJdbcDao;
    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() throws DuplicateUsernameException {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "match");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "tournament");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "player");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"game");
        JdbcTestUtils.deleteFromTables(jdbcTemplate,"users");
        jdbcTemplate.execute("INSERT INTO users VALUES (1, 'Pibe', 'contraseña')");
        jdbcTemplate.execute("INSERT INTO users VALUES (2, 'Pibe2', 'contraseña')");
        jdbcTemplate.execute("INSERT INTO player VALUES (1, 'Jugador1', 1)");
        jdbcTemplate.execute("INSERT INTO player VALUES (2,  'Jugador2', 2)");
        jdbcTemplate.execute("INSERT INTO game VALUES (1, 'Smash', true)");
        jdbcTemplate.execute("INSERT INTO game VALUES (2, 'Smosh', true)");
        jdbcTemplate.execute("INSERT INTO tournament VALUES (1, 'NEW', 'Torneo', 1, 1)");
        jdbcTemplate.execute("INSERT INTO tournament VALUES (2, 'NEW', 'Torneo2', 2, 2)");
        //final User user = userJdbcDao.create("Kachow", "asddas");
        //final Player dummy = playerJdbcDao.create("Dummy");
        //final Player player1 = playerJdbcDao.create("Alex");
        //final Player player2 = playerJdbcDao.create("Alexis");
        //final Game game = gameJdbcDao.create("Smash", true);
        //final Tournament tourney = tournamentJdbcDao.create("Prueba", game.getGameId(), 0);
        //final Match match = matchDao.create(1,0,true, 0, 1, 2, 17);
    }

    @Test
    public void testFindTournamentSuccess() throws DuplicateUsernameException {
       // final User user = userJdbcDao.create("Kachow", "asddas");
       // final Player dummy = playerJdbcDao.create("Dummy");
       // final Player player1 = playerJdbcDao.create("Alex");
       // final Player player2 = playerJdbcDao.create("Alexis");
        //final Game game = gameJdbcDao.create("Smash", true);
        //final Tournament tourney = tournamentJdbcDao.create("Prueba", game.getGameId(), 0);
        //final Match match = matchDao.create(1,0,true, 0, 1, 2, 17);
        //assertNotNull(tourney);
        //assertEquals(0, tourney.getId());
        //tournamentJdbcDao.setStatus(0, Tournament.Status.FINISHED);
        Tournament tournament = tournamentJdbcDao.findById(1);
        assertNotNull(tournament);
       // assertEquals(Tournament.Status.FINISHED, tournament.getStatus());
    }


    @Test
    public void testFindTournamentFailure() {
        assertNull(tournamentJdbcDao.findById(233));
    }

    @Test
    public void testFinish() {
        tournamentJdbcDao.setStatus(1, Tournament.Status.FINISHED);
        assertEquals(Tournament.Status.FINISHED, tournamentJdbcDao.findById(1).getStatus());
    }

    @Test
    public void testGetTournamentByNameSuccess() {
        assertNotNull(tournamentJdbcDao.getByName("Torneo"));
    }

    @Test
    public void testGetTournamentByNameFailure() {
        assertNull(tournamentJdbcDao.getByName("asdasd"));
    }

    @Test
    public void testStartTournamentStatus() {
        tournamentJdbcDao.setStatus(1, Tournament.Status.STARTED);
        assertEquals(Tournament.Status.STARTED, tournamentJdbcDao.findById(1).getStatus());
    }

    @Test
    public void testSearchByNameQuery() {
       assertEquals(2, tournamentJdbcDao.findTournamentNames("tor").size());
    }

    @Test
    public void testSearchByGameQuery() {
        assertEquals(1, tournamentJdbcDao.findTournamentNames("tor", 1).size());
    }
}
