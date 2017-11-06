import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.interfaces.service.GameService;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Standing;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.service.PlayerServiceImpl;
import ar.edu.itba.paw.service.TournamentServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Null;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.List;

public class TournamentServiceTest {
/*
    @Mock
    TournamentDao tournamentDao;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @InjectMocks
    TournamentServiceImpl tournamentServiceImpl;
    private int FEATURED_TOURNAMENTS = 10;

    @Before
    public void setUp() {
        Mockito.when(tournamentDao.findById(3)).thenReturn(standardTournament());
        Mockito.when(tournamentDao.findById(1)).thenReturn(null);
        Mockito.when(tournamentDao.create("Test", -1, 1)).thenReturn(standardNoGameTournament());
        Mockito.when(tournamentDao.create("Test",2, 1)).thenReturn(standardTournament());
        Mockito.when(tournamentDao.findFeaturedTournaments(FEATURED_TOURNAMENTS)).thenReturn(standardTournaments());
        Mockito.when(tournamentDao.findFeaturedTournaments(0)).thenReturn(new ArrayList<>());
        Mockito.when(tournamentDao.getStandings(3)).thenReturn(mockStanding());
    }

    @Test
    public void testCreate() {
        Tournament tournament = tournamentServiceImpl.create("Test", 2, 1);
        Assert.assertEquals("Test", tournament.getName());
    }

    @Test
    public void testCreateNoGameTournament() {
        Tournament tournament = tournamentServiceImpl.create("Test", -1, 1);
        Assert.assertNotNull(tournament);
    }

    @Test
    public void testFindById() {
        Tournament tournament = tournamentServiceImpl.findById(3);
        Assert.assertEquals(3, tournament.getId());
    }

    @Test
    public void tournamentNotFound() {
        Tournament tournament = tournamentServiceImpl.findById(1);
        Assert.assertNull(tournament);
    }

    @Test
    public void testStandingsReturn() {
        List<Standing> standings = tournamentServiceImpl.getStandings(3);
        Assert.assertEquals(1, standings.get(0).getPosition());
    }

    @Test
    public void testFindFeaturedTournaments() {
        List<Tournament> tournaments = tournamentServiceImpl.findFeaturedTournaments(FEATURED_TOURNAMENTS);
        for (Integer i = 0; i < FEATURED_TOURNAMENTS; i++) {
            Assert.assertEquals(i.toString(), tournaments.get(i).getName());
            Assert.assertEquals(i.longValue(), tournaments.get(i).getId());

        }
    }

    @Test
    public void testNoFeaturedTournaments() {
        List<Tournament> tournaments = tournamentServiceImpl.findFeaturedTournaments(0);
        Assert.assertEquals(0, tournaments.size());
    }

    private Tournament standardTournament() {
        return new Tournament("Test", 3, 1, Tournament.Status.NEW, 1);
    }

    private Tournament standardNoGameTournament() {
        return new Tournament("Test", 3, -1, Tournament.Status.NEW, 1);
    }

    private List<Tournament> standardTournaments() {
        List<Tournament> tournaments = new ArrayList<Tournament>();
        for(Integer i = 0; i < FEATURED_TOURNAMENTS; i++) {
            tournaments.add(new Tournament(i.toString(), i,1, Tournament.Status.NEW, 1));
        }
        return tournaments;
    }

    private List<Standing> mockStanding() {
        List<Standing> standings = new ArrayList<>();
        standings.add(new Standing("Primero", 1));
        standings.add(new Standing("Segundo", 2));
        standings.add(new Standing("Tercero", 3));
        standings.add(new Standing("Cuarto", 4));
        standings.add(new Standing("Quinto", 5));
        standings.add(new Standing("Quintos", 5));
        standings.add(new Standing("Septimo", 7));
        standings.add(new Standing("Septimos", 7));
        return standings;
    }
    */
}
