package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.interfaces.persistence.DuplicateUsernameException;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.*;
import jdk.nashorn.internal.scripts.JD;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class MatchJdbcDaoTest {

    @Autowired
    private DataSource ds;

    @Autowired
    private MatchHibernateDao matchDao;
    @Autowired
    private PlayerHibernateDao playerDao;
    @Autowired
    private TournamentHibernateDao tournamentDao;
    @Autowired
    private UserHibernateDao userDao;
    @Autowired
    private GameHibernateDao gameDao;

    private int MATCH_ID = 1;
    private int TOURNAMENT_ID = 1;

    @Before
    public void setUp() throws ConstraintViolationException{
        try {
            Tournament tournament;
            Game game;
            User user;
            user = userDao.create("Pibe", "contraseña");
            userDao.create("Pibe2", "contraseña");
            game = gameDao.create("Smash", false);
            tournament = tournamentDao.create("Torneo", game.getId(), user.getId());
            playerDao.create("Jugador", tournament);
            playerDao.create("Jugador2", tournament);
            matchDao.create(MATCH_ID,0,false,tournament.getId(),1,2, 1);

        }
        catch (ConstraintViolationException e) {
            matchDao.create(MATCH_ID,0,false,TOURNAMENT_ID,1,2, 1);
        }
        /*User user = new User("Pibe", "contraseña");
        User user2 = new User("Pibe2", "contraseña");
        Game game = new Game("Smash", true);
        Tournament tournament = new Tournament("Torneo", game, Tournament.Status.STARTED, user);
        Tournament tournament2 = new Tournament("Torneo2", game, Tournament.Status.STARTED, user);
        Player player = new Player("Jugador1", tournament);
        Player player2 = new Player("Jugador2", tournament);
        Match match = new Match(1, null,false,tournament,1);
        Match match1 = new Match(2, match, true, tournament,2);
        em.persist(user);
        em.persist(user2);
        em.persist(game);
        em.persist(tournament);
        em.persist(tournament2);
        em.persist(player);
        em.persist(player2);
        em.persist(match);
        em.persist(match1);*/
    }

    //@After
    //public void removeData() {
        //matchDao.getEntityManager().createNativeQuery("delete from match");
        //playerDao.getEntityManager().createNativeQuery("delete from player");
        //tournamentDao.getEntityManager().createNativeQuery("delete from tournament");
        //gameDao.getEntityManager().createNativeQuery("delete from game");
      //  userDao.getEntityManager().createNativeQuery("delete from users");
    //}

    @Test
    @Transactional
    public void testFindByIdSuccess() throws DuplicateUsernameException {
        assertNotNull(matchDao.findById(MATCH_ID,TOURNAMENT_ID));
    }

    @Test
    @Transactional
    public void testFindByIdFailure() {
        assertNull(matchDao.findById(4,TOURNAMENT_ID));
    }

    @Test
    @Transactional
    public void testUpdateMatchAwayScore() {
        matchDao.updateScore(TOURNAMENT_ID,MATCH_ID,1,2);
        assertEquals(2, (long)matchDao.findById(MATCH_ID,TOURNAMENT_ID).getAwayPlayerScore());
    }



    @Test
    @Transactional
    public void testGetTournamentMatches() {
        assertEquals(1, matchDao.getTournamentMatches(TOURNAMENT_ID).size());
    }

    @Test
    @Transactional
    public void testGetStanding() {
        assertEquals(1, matchDao.findById(MATCH_ID,TOURNAMENT_ID).getStanding());
    }

}
