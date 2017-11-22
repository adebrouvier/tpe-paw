import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.service.TournamentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

public class TournamentServiceTest {

    @Mock
    TournamentDao tournamentDao;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @InjectMocks
    TournamentServiceImpl tournamentServiceImpl;
    private int FEATURED_TOURNAMENTS = 10;
    private final String TOURNAMENT_NAME = "Torneo";
    private final int MATCH_ID = 1;
    private final String PASSWORD = "serenito";
    private final String USERNAME = "postre";

    @Before
    public void setUp() {
        Mockito.when(tournamentDao.findById(0)).thenReturn(standardTournament());
        Mockito.when(tournamentDao.findById(1)).thenReturn(null);
        Mockito.when(tournamentDao.create("Test", -1, 1)).thenReturn(standardNoGameTournament());
        Mockito.when(tournamentDao.create("Test", 2, 1)).thenReturn(standardTournament());
        Mockito.when(tournamentDao.findFeaturedTournaments(FEATURED_TOURNAMENTS)).thenReturn(standardTournaments());
        Mockito.when(tournamentDao.findFeaturedTournaments(0)).thenReturn(new ArrayList<>());
    }

    @Test
    public void testCreate() {
        Tournament tournament = tournamentServiceImpl.create("Test", 2, 1);
        Assert.assertEquals(TOURNAMENT_NAME, tournament.getName());
    }

    @Test
    public void testCreateNoGameTournament() {
        Tournament tournament = tournamentServiceImpl.create("Test", -1, 1);
        Assert.assertNotNull(tournament);
    }

    @Test
    public void testFindById() {
        Tournament tournament = tournamentServiceImpl.findById(0);
        Assert.assertEquals(0, tournament.getId());
    }

    @Test
    public void tournamentNotFound() {
        Tournament tournament = tournamentServiceImpl.findById(1);
        Assert.assertNull(tournament);
    }


    @Test
    public void testFindFeaturedTournaments() {
        List<Tournament> tournaments = tournamentServiceImpl.findFeaturedTournaments(FEATURED_TOURNAMENTS);
        for (Integer i = 0; i < FEATURED_TOURNAMENTS; i++) {
            Assert.assertEquals(TOURNAMENT_NAME, tournaments.get(i).getName());
        }
    }

    @Test
    public void testNoFeaturedTournaments() {
        List<Tournament> tournaments = tournamentServiceImpl.findFeaturedTournaments(0);
        Assert.assertEquals(0, tournaments.size());
    }

    private Tournament standardTournament() {
        return new Tournament(TOURNAMENT_NAME, null, Tournament.Status.STARTED, new User(USERNAME, PASSWORD));
    }

    private Tournament standardNoGameTournament() {
        return new Tournament(TOURNAMENT_NAME, null, Tournament.Status.STARTED, new User(USERNAME, PASSWORD));
    }

    private List<Tournament> standardTournaments() {
        List<Tournament> tournaments = new ArrayList<Tournament>();
        for (Integer i = 0; i < FEATURED_TOURNAMENTS; i++) {
            tournaments.add(new Tournament(TOURNAMENT_NAME, null, Tournament.Status.STARTED, new User(USERNAME, PASSWORD)));
        }
        return tournaments;
    }

}
