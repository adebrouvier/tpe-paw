import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.service.MatchServiceImpl;
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

public class MatchServiceTest {

    @Mock
    MatchDao matchDao;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @InjectMocks
    MatchServiceImpl matchServiceImpl;

    @Before
    public void setUp() {
        Mockito.when(matchDao.create(1,2, true, 10, 3,4)).thenReturn(mockMatch());
        Mockito.when(matchDao.createEmpty(1, 2, true, 10)).thenReturn(mockMatch());
        Mockito.when(matchDao.findById(1,10)).thenReturn(mockMatch());
        Mockito.when(matchDao.getTournamentMatches(10)).thenReturn(mockMatches());
        Mockito.when(matchDao.updateScore(10, 1, 1, 0)).thenReturn(mockMatchUpdated());
    }

    @Test
    public void testCreate() {
        Match match = matchServiceImpl.create(1,2,true,10,3,4);
        Assert.assertEquals(1, match.getId());
        Assert.assertEquals(2, match.getNextMatchId());
        Assert.assertEquals(true, match.isNextMatchHome());
        Assert.assertEquals(10, match.getTournamentId());
        Assert.assertEquals(3, match.getHomePlayerId());
        Assert.assertEquals(4, match.getAwayPlayerId());
    }


    @Test
    public void testCreateEmpty() {
        Match match = matchServiceImpl.createEmpty(1,2,true,10);
        Assert.assertEquals(1, match.getId());
        Assert.assertEquals(2, match.getNextMatchId());
        Assert.assertEquals(true, match.isNextMatchHome());
        Assert.assertEquals(10, match.getTournamentId());
    }

    @Test
    public void testFindById() {
        Match match = matchServiceImpl.findById(1,10);
        Assert.assertEquals(1, match.getId());
        Assert.assertEquals(2, match.getNextMatchId());
        Assert.assertEquals(true, match.isNextMatchHome());
        Assert.assertEquals(10, match.getTournamentId());
        Assert.assertEquals(3, match.getHomePlayerId());
        Assert.assertEquals(4, match.getAwayPlayerId());
    }

    @Test
    public void testUpdate() {
        Match match = matchServiceImpl.updateScore(10,1,1,0);
        Assert.assertEquals(1, match.getId());
        Assert.assertEquals(2, match.getNextMatchId());
        Assert.assertEquals(true, match.isNextMatchHome());
        Assert.assertEquals(10, match.getTournamentId());
        Assert.assertEquals(3, match.getHomePlayerId());
        Assert.assertEquals(4, match.getAwayPlayerId());
    }
    private Match mockMatch() {
        return new Match(3, 4, 1, 2, true, 10);
    }

    private List<Match> mockMatches() {
        List<Match> matches = new ArrayList<Match>();
        for(int i = 0; i < 10; i++) {
            matches.add(mockMatch());
        }
        return matches;
    }

    private Match mockMatchUpdated() {
        Match match =  new Match(3, 4, 1, 2, true, 10);
        match.setHomePlayerScore(1);
        return match;
    }
}
