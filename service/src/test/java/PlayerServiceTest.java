import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.service.PlayerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class PlayerServiceTest {
    @Mock
    PlayerDao playerDao;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @InjectMocks
    PlayerServiceImpl playerServiceImpl;

    @Before
    public void setUp() {
        Mockito.when(playerDao.create("jorgito")).thenReturn(createPlayer());
        Mockito.when(playerDao.create(null)).thenReturn(null);
        Mockito.when(playerDao.findBySeed(1,1)).thenReturn((long)1);
        Mockito.when(playerDao.findBySeed(19,1)).thenReturn((long)PlayerDao.EMPTY);
        Mockito.when(playerDao.addToTournament(1,1,1)).thenReturn(true);
        Mockito.when(playerDao.addToTournament(1,1,13)).thenReturn(false);
        Mockito.when(playerDao.findById(1)).thenReturn(createPlayer());
        Mockito.when(playerDao.findById(233)).thenReturn(null);
    }

    @Test
    public void testCreate() {
        Player player = playerServiceImpl.create("jorgito");
        Assert.assertEquals("jorgito", player.getName());
        Mockito.verify(playerDao).create("jorgito");
    }

    @Test
    public void testCreateNull() {
        Player player = playerServiceImpl.create(null);
        Assert.assertNull(player);
    }

    @Test
    public void testPlayerFoundBySeed() {
        Assert.assertEquals(1, playerServiceImpl.findBySeed(1,1));
    }

    @Test
    public void testPlayerNotFoundBySeed() {
        Assert.assertEquals(PlayerDao.EMPTY, playerServiceImpl.findBySeed(1,19));
    }

    @Test
    public void testAddToTournamentFailure() {
        Assert.assertEquals(false, playerServiceImpl.addToTournament(1,1,13));
    }

    @Test
    public void testAddToTournamentSuccess() {
        Assert.assertEquals(true, playerServiceImpl.addToTournament(1,1,1));
    }

    @Test
    public void testPlayerFoundById() {
        Assert.assertNotNull(playerServiceImpl.findById(1));
    }

    @Test
    public void testPlayerNotFoundById() {
        Assert.assertNull(playerServiceImpl.findById(233));
    }

    private Player createPlayer() {
        Player player = new Player("jorgito", 1);
        return player;
    }

}
