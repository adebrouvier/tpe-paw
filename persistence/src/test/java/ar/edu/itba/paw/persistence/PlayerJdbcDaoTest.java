package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.User;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class PlayerJdbcDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private PlayerJdbcDao playerDao;
    private JdbcTemplate jdbcTemplate;


    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "participates_in");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "tournament");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "player");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "users");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, "game");
        jdbcTemplate.execute("INSERT INTO users VALUES (1, 'Pibe', 'contraseña')");
        jdbcTemplate.execute("INSERT INTO users VALUES (2, 'Pibe2', 'contraseña')");
        jdbcTemplate.execute("INSERT INTO player VALUES (1, 'Jugador1', 1)");
        jdbcTemplate.execute("INSERT INTO player VALUES (2,  'Jugador2', 2)");
        jdbcTemplate.execute("INSERT INTO game VALUES (1, 'Smash', true)");
        jdbcTemplate.execute("INSERT INTO game VALUES (2, 'Smosh', true)");
        jdbcTemplate.execute("INSERT INTO tournament VALUES (1, 'NEW', 'Torneo', 1, 1)");
        jdbcTemplate.execute("INSERT INTO tournament VALUES (2, 'NEW', 'Torneo2', 2, 2)");
        jdbcTemplate.execute("INSERT INTO participates_in VALUES (1,1,1,3)");
        jdbcTemplate.execute("INSERT INTO participates_in VALUES (2,1,1,3)");
    }

    @Test
    public void testCreate() {
        final Player player = playerDao.create("Jorge");
        assertNotNull(player);
        assertEquals(3, JdbcTestUtils.countRowsInTable(jdbcTemplate, "player"));
    }

    @Test
    public void testFindById() {
        assertNotNull(playerDao.findById(1));
    }

    @Test
    public void testGetTournamentPlayers() {
        playerDao.getTournamentPlayers(1);
    }

}