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
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql("classpath:schema.sql")
public class MatchHibernateDaoTest {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private DataSource ds;

    @Autowired
    private MatchHibernateDao matchDao;
    @Autowired
    private PlayerHibernateDao playerDao;
    @Autowired
    private TournamentHibernateDao tournamentDao;

    private int MATCH_ID = 1;
    private long TOURNAMENT_ID = 1;

    @Before
    public void setUp() throws ConstraintViolationException{
            User user = new User("Pibe", "contraseña");
            User user2 = new User("Pibe2", "contraseña");
            Game game = new Game("Smash", true);
            Tournament tournament = new Tournament("Torneo", game, Tournament.Status.STARTED, user);
            Tournament tournament2 = new Tournament("Torneo2", game, Tournament.Status.STARTED, user);
            Player player = new Player("Jugador1", tournament);
            Player player2 = new Player("Jugador2", tournament);
            Match match = new Match(MATCH_ID,player, player2,0,0, null, false, tournament,1);
            Match match1 = new Match(MATCH_ID+1,player,player2,0,0, match, true, tournament,2);
            final Map<Tournament, Integer> criteria = new HashMap<>();
            criteria.put(tournamentDao.findById(1), 100);
            Ranking ranking = new Ranking("Ranking", game,user);
            List<UserScore> userScoreList = new ArrayList<>();
            userScoreList.add(new UserScore(ranking, user,100));
            ranking.setUserScores(userScoreList);
            List<TournamentPoints> tournamentPoints = new ArrayList<>();
            tournamentPoints.add(new TournamentPoints(ranking, tournament, 1000));
            ranking.setTournaments(tournamentPoints);
            em.persist(user);
            em.persist(user2);
            em.persist(game);
            em.persist(tournament);
            em.persist(tournament2);
            em.persist(player);
            em.persist(player2);
            em.persist(match);
            em.persist(match1);
            em.persist(ranking);
            em.flush();
        }


    @After
    public void removeData() {
        //em.createNativeQuery("delete from match");
        //em.createNativeQuery("delete from player");
        //em.createNativeQuery("delete from tournament");
        //em.createNativeQuery("delete from game");
        //em.createNativeQuery("delete from users");
        //em.flush();
    }

    @Test
    @Transactional
    public void testFindByIdSuccess() throws DuplicateUsernameException {
        final TypedQuery<Match> query = em.createQuery("from Match as m " +
                "where m.id = :id", Match.class);
        query.setParameter("id", MATCH_ID);
        final List<Match> list = query.getResultList();
        assertNotNull(list.get(0));
    }

    @Test
    @Transactional
    public void testFindByIdFailure() {
        assertNull(matchDao.findById(4,TOURNAMENT_ID));
    }


    @Test
    @Transactional
    public void testGetTournamentMatches() {
        assertEquals(2, matchDao.getTournamentMatches(TOURNAMENT_ID).size());
    }

    @Test
    @Transactional
    public void testGetStanding() {
        final TypedQuery<Match> query = em.createQuery("from Match as m " +
                "where m.id = :id", Match.class);
        query.setParameter("id", MATCH_ID);
        final List<Match> list = query.getResultList();
        assertEquals(1, list.get(0).getStanding());
    }

}
