import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Player;
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

    @Before
    public void setUp() {
        Mockito.when(tournamentDao.findById(3)).thenReturn(standardTournament());
        Mockito.when(tournamentDao.create("Test","Game", 1)).thenReturn(standardTournament());
        Mockito.when(tournamentDao.findFeaturedTournaments()).thenReturn(standardTournaments());
    }

    @Test
    public void testCreate() {
        Tournament tournament = tournamentServiceImpl.create("Test", "Game", 1);
        Assert.assertEquals("Test", tournament.getName());

    }

    @Test
    public void testFindById() {
        Tournament tournament = tournamentServiceImpl.findById(3);
        Assert.assertEquals(3, tournament.getId());

    }

    @Test
    public void testFindFeaturedTournaments() {
        List<Tournament> tournaments = tournamentServiceImpl.findFeaturedTournaments();
        for (Integer i = 0; i < 10; i++) {
            Assert.assertEquals(i.toString(), tournaments.get(i).getName());
            Assert.assertEquals(i.longValue(), tournaments.get(i).getId());

        }
    }
    private Tournament standardTournament() {
        return new Tournament("Test", 3, 1, Tournament.Status.NEW, 1);
    }

    private List<Tournament> standardTournaments() {
        List<Tournament> tournaments = new ArrayList<Tournament>();
        for(Integer i = 0; i < 10; i++) {
            tournaments.add(new Tournament(i.toString(), i,1, Tournament.Status.NEW, 1));
        }
        return tournaments;
    }
}
