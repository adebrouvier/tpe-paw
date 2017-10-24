import ar.edu.itba.paw.interfaces.persistence.GameDao;
import ar.edu.itba.paw.interfaces.persistence.RankingDao;
import ar.edu.itba.paw.model.Game;
import ar.edu.itba.paw.service.GameServiceImpl;
import ar.edu.itba.paw.service.RankingServiceImpl;
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class GameServiceTest {
    @Mock
    GameDao gameDao;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @InjectMocks
    GameServiceImpl gameService;

    @Before
    public void setUp() {
        Mockito.when(gameDao.create("Smash", false)).thenReturn(generatedGame("Smash",false));
        Mockito.when(gameDao.create("Smash", true)).thenReturn(generatedGame("Smash",true));
        Mockito.when(gameDao.create(null,true)).thenReturn(null);
        Mockito.when(gameDao.findById(1)).thenReturn(generatedGame("Smash",false));
        Mockito.when(gameDao.findById(123)).thenReturn(null);
        Mockito.when(gameDao.findGameNames("Brawl")).thenReturn(generatedGameNames("Brawl",true));
        Mockito.when(gameDao.findGameNames("asdasdasf")).thenReturn(null);
    }


    @Test
    public void testFindByIdSuccess() {
        Game game = gameService.findById(1);
        assertEquals("Smash", game.getName());
    }

    @Test
    public void testFindByIdFailure() {
        Game game = gameService.findById(123);
        assertNull(game);
    }

    @Test
    public void testCreateEmptyGame() {
        Game game = gameService.create(null, true);
        assertNull(game);
    }

    @Test
    public void testFindGameByName() {
        Game game = gameService.findById(1);
        assertEquals("Smash", game.getName());
    }

    @Test
    public void testFindGamesByName() {
        List<String> brawlers = gameService.findGameNames("Brawl");
        assertEquals("Brawl", brawlers.get(0));
    }

    @Test
    public void testFindGamesByNameNoGamesMatch() {
        List<String> brawlers = gameService.findGameNames("asdasdasf");
        assertNull(brawlers);
    }

    public Game generatedGame(String gameName, Boolean userGenerated) {
        return new Game(1, gameName);
    }
    public List<String> generatedGameNames(String gameName, Boolean userGenerated) {
        List<String> gamesName = new ArrayList<>();
        gamesName.add(gameName);
        return gamesName;
    }
}

