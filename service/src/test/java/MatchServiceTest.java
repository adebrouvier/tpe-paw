import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
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

  private final String TOURNAMENT_NAME = "Torneo";
  private final int MATCH_ID = 1;
  private final String PASSWORD = "serenito";
  private final String USERNAME = "postre";

  @Before
  public void setUp() {
    Mockito.when(matchDao.create(1, 2, true, 10, 3, 4, 17)).thenReturn(mockMatch(3, 4, 1, 2, true, 10, 17));
    Mockito.when(matchDao.createEmpty(1, 2, true, 10, 17)).thenReturn(mockMatch(3, 4, 1, 2, true, 10, 17));
    Mockito.when(matchDao.findById(1, 10)).thenReturn(mockMatch(3, 4, 1, 2, true, 10, 17));
    Mockito.when(matchDao.getTournamentMatches(10)).thenReturn(mockMatches(3, 4, true, 10, 17));
    Mockito.when(matchDao.updateScore(10, 1, 1, 0)).thenReturn(mockMatchUpdated(1, 0));
    Mockito.when(matchDao.create(1, TournamentService.NO_PARENT, TournamentService.FINAL_NEXT_MATCH_HOME, 10, 3, 4, 17)).thenReturn(mockMatch(3, 4, 1, TournamentService.NO_PARENT, TournamentService.FINAL_NEXT_MATCH_HOME, 10, 17));
  }

  @Test
  public void testCorrectMatchIdCreation() {
    Match match = matchServiceImpl.create(1, 2, true, 10, 3, 4, 17);
    Assert.assertEquals(1, match.getId());
  }

  @Test
  public void testCorrectTournamentIdCreation() {
    Match match = matchServiceImpl.create(1, 2, true, 10, 3, 4, 17);
    Assert.assertEquals(0, match.getTournament().getId());
  }

  @Test
  public void testCorrectHomePlayerIdCreation() {
    Match match = matchServiceImpl.create(1, 2, true, 10, 3, 4, 17);
    Assert.assertEquals(new Player(USERNAME, null, null), match.getHomePlayer());
  }

  @Test
  public void testCorrectAwayPlayerIdCreation() {
    Match match = matchServiceImpl.create(1, 2, true, 10, 3, 4, 17);
    Assert.assertEquals(new Player(USERNAME, null, null), match.getAwayPlayer());
  }

  @Test
  public void testCorrectIsNextMatchHomeCreation() {
    Match match = matchServiceImpl.create(1, 2, true, 10, 3, 4, 17);
    Assert.assertEquals(true, match.isNextMatchHome());
  }

  @Test
  public void testCreateEmptyCorrect() {
    Match match = matchServiceImpl.createEmpty(1, 2, true, 10, 17);
    Assert.assertEquals(1, match.getId());
    Assert.assertEquals(true, match.isNextMatchHome());
    Assert.assertEquals(0, match.getTournament().getId());
    Assert.assertEquals(2, match.getStanding());
  }

  @Test
  public void testFindByIdCorrect() {
    Match match = matchServiceImpl.findById(1, 10);
    Assert.assertEquals(1, match.getId());
    Assert.assertEquals(true, match.isNextMatchHome());
    Assert.assertEquals(0, match.getTournament().getId());
    Assert.assertEquals(new Player(USERNAME, null, null), match.getHomePlayer());
    Assert.assertEquals(new Player(PASSWORD, null, null), match.getAwayPlayer());
  }

  @Test
  public void testUpdateCorrect() {
    Match match = matchServiceImpl.updateScore(10, 1, 1, 0);
    Assert.assertEquals(1, match.getId());
    Assert.assertEquals(true, match.isNextMatchHome());
    Assert.assertEquals(1, (long) match.getHomePlayerScore());
  }

  @Test
  public void testCreateFinalMatch() {
    Match match = matchServiceImpl.create(1, TournamentService.NO_PARENT, TournamentService.FINAL_NEXT_MATCH_HOME, 10, 3, 4, 17);
    Assert.assertNull(match.getNextMatch());
  }

  private Match mockMatch(int homePlayerId, int awayPlayerId, int matchId, int nextMatchId, boolean nextMatchHome, int tournamentId, int standing) {

    return new Match(MATCH_ID, new Player(USERNAME, null, null), new Player(PASSWORD, null, null), null, true, new Tournament(TOURNAMENT_NAME, null, Tournament.Status.STARTED, new User(USERNAME, PASSWORD)), 2);
  }

  private List<Match> mockMatches(int homePlayerId, int awayPlayerId, boolean nextMatchHome, int tournamentId, int standing) {
    List<Match> matches = new ArrayList<Match>();
    for (int i = 0; i < 10; i++) {
      matches.add(mockMatch(3, 4, i, i + 1, true, 10, 17));
    }
    return matches;
  }

  private Match mockMatchUpdated(int homeScore, int awayScore) {
    Match match = new Match(MATCH_ID, null, true, new Tournament(TOURNAMENT_NAME, null, Tournament.Status.STARTED, new User(USERNAME, PASSWORD)), 1);
    match.setHomePlayerScore(1);
    return match;
  }
}
