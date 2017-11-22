import ar.edu.itba.paw.interfaces.persistence.RankingDao;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserScore;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RankingServiceTest {
    @Mock
    RankingDao rankingDao;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @InjectMocks
    RankingServiceImpl rankingService;

    private final String GAME = "Smash";

    private final Map<Tournament, Integer> tournaments = new HashMap<>();

    @Before
    public void setUp() {
        Mockito.when(rankingDao.create("ranking", tournaments, GAME, 1)).thenReturn(standardRanking(1));
        Mockito.when(rankingDao.addTournaments(1, tournaments)).thenReturn(standardRanking(1));
        Mockito.when(rankingDao.findById(1)).thenReturn(standardRanking(1));
        Mockito.when(rankingDao.findByName("ranking", GAME)).thenReturn(standardRankings());
    }

    @Test
    public void testCreate() {
        Ranking ranking = rankingService.create("ranking", tournaments, GAME, 1);
        assertEquals("ranking", ranking.getName());
        assertEquals(200, ranking.getUserScores().get(1).getPoints());
    }


    @Test
    public void testFindMethodSuccess() {
        Ranking ranking = rankingService.findById(1);
        assertEquals("ranking", ranking.getName());
    }

    @Test
    public void testGetPoints() {
        assertEquals(200, rankingService.findById(1).getUserScores().get(1).getPoints());
    }

    @Test
    public void testGetRankingsSuccess() {
        List<Ranking> rankings = rankingService.findByName("ranking", GAME);
        assertEquals(10, rankings.size());
        assertEquals(200, rankings.get(2).getUserScores().get(1).getPoints());
    }

    private Ranking standardRanking(int id) {
        Ranking rank = new Ranking("ranking", null, null);
        List<UserScore> userScores = new ArrayList<>();
        userScores.add(new UserScore(rank, new User("Alexis", "Moragues"), 100));
        userScores.add(new UserScore(rank, new User("Alexis2", "Moragues"), 200));
        userScores.add(new UserScore(rank, new User("Alexis3", "Moragues"), 40));
        rank.setUserScores(userScores);
        return rank;
    }

    private List<Ranking> standardRankings() {
        List<Ranking> rank = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            rank.add(standardRanking(i));
        }
        return rank;
    }

}
