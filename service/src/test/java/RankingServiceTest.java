import ar.edu.itba.paw.interfaces.persistence.RankingDao;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
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
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class RankingServiceTest {
/*    @Mock
    RankingDao rankingDao;
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    @InjectMocks
    RankingServiceImpl rankingService;

   Map<Tournament, Integer> tournaments;
    @Before
    public void setUp() {
        Mockito.when(rankingDao.create("ranking", tournaments, "Smash", 1)).thenReturn(standardRanking(1));
        Mockito.when(rankingDao.addTournaments(1, tournaments)).thenReturn(standardRanking(1));
        Mockito.when(rankingDao.findById(1)).thenReturn(standardRanking(1));
        Mockito.when(rankingDao.findByName("ranking")).thenReturn(standardRankings());
    }

    @Test
    public void testCreate() {
        Ranking ranking = rankingService.create("ranking", tournaments, "Smash", 1);
        assertEquals("ranking", ranking.getName());
        assertEquals(200, ranking.getUsers().get(1).getPoints());
    }

    @Test
    public void testAddTournament() {
        Ranking ranking = rankingService.addTournaments(1, tournaments);
        assertEquals(1, ranking.getId());
    }

    @Test
    public void testFindMethodSuccess() {
        Ranking ranking = rankingService.findById(1);
        assertEquals("ranking", ranking.getName());
    }

    @Test
    public void testGetPoints() {
        assertEquals(200, rankingService.findById(1).getUsers().get(1).getPoints());
    }

    @Test
    public void testGetRankingsSuccess() {
        List<Ranking> rankings = rankingService.findByName("ranking");
        assertEquals(10, rankings.size());
        assertEquals(200, rankings.get(2).getUsers().get(1).getPoints());
    }

    private Ranking standardRanking(int id) {
        Ranking rank = new Ranking(id,"ranking", 1, 1);
        List<UserScore> userScores = new ArrayList<>();
        userScores.add(new UserScore(1, 100));
        userScores.add(new UserScore(2,200));
        userScores.add(new UserScore(3, 40));
        rank.setUsers(userScores);
        return rank;
    }

    private List<Ranking> standardRankings() {
        List<Ranking> rank = new ArrayList<>();
        for(int i=0; i<10; i++) {
            rank.add(standardRanking(i));
        }
        return rank;
    }
*/

}
