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
        Mockito.when(playerDao.create("jorgito")).thenReturn(createTest());
    }

    @Test
    public void test() {

        Player player = playerServiceImpl.create("jorgito");
        Assert.assertEquals("jorgito", player.getName());
        Mockito.verify(playerDao).create("jorgito");
    }

    private Player createTest() {
        Player player = new Player("jorgito", 1);
        return player;
    }

}
