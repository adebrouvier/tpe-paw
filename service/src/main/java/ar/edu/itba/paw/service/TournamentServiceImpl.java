package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Standing;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @Override
    public Tournament findById(long id) {
        return tournamentDao.findById(id);
    }

    @Override
    public void setStatus(long tournamentId, Tournament.Status status) {
        tournamentDao.setStatus(tournamentId, status);
    }

    @Override
    public Tournament create(String name, String game, long userId) {
        return tournamentDao.create(name, game, userId);
    }

    @Override
    public List<Tournament> findFeaturedTournaments() {
        return tournamentDao.findFeaturedTournaments();
    }

    @Override
    public List<Standing> getStandings(long tournamentId) {
        return tournamentDao.getStandings(tournamentId);
    }

    @Override
    public List<String> findTournamentNames(String query) {
        return tournamentDao.findTournamentNames(query);
    }

    @Override
    public List<Tournament> findByName(String name) {
        return tournamentDao.findByName(name);
    }

    @Override
    public Tournament getByName(String name) {
        return tournamentDao.getByName(name);
    }

    @Override
    public void generateBracket(long tournamentId){

        List<Player> players = playerService.getTournamentPlayers(tournamentId);
        int playerCount = players.size();
        int power = (int) Math.ceil(Math.log(playerCount / Math.log(2)));
        int byes = (int) (Math.pow(2, power) - playerCount);

        for (int i = playerCount; i < playerCount + byes; i++) {
            playerService.addToTournament(BYE_ID, tournamentId);
            players.add(new Player(BYE_NAME, BYE_ID));
        }
        /*TODO:tournament.getMatches() is empty. The tournament in return is not updated*/
        generateSingleEliminationBracket(tournamentId, players);
    }

    /**
     * Generates every match in the Tournament bracket.
     * Setting its initial standing, calculating seeds placement
     * and generating bracket relations of every match.
     * @param tournamentId id of the tournament
     * @param players list of players
     */
    private void generateSingleEliminationBracket(long tournamentId, List<Player> players) {
        int totalDepth = (int) (Math.log(players.size()) / Math.log(2)); /* Size should always be a power of 2*/
        playerService.setDefaultStanding(players.size() / 2 + 1, tournamentId);
        generateBracketRecursive(TournamentService.FIRST_SEED, TournamentService.FIRST_ROUND, TournamentService.FIRST_MATCH_ID, TournamentService.NO_PARENT, TournamentService.FINAL_NEXT_MATCH_HOME, tournamentId, (int) Math.pow(2, totalDepth), TournamentService.INITIAL_STANDING);
    }

    private void generateBracketRecursive(int seed, int roundPlayers, int matchId, int parentId, boolean isNextMatchHome, long tournamentId, int totalPlayers, int standing) {
        if (roundPlayers > totalPlayers) {
            return;
        }

        int nextStanding = standing * 2 - (standing == 1 ? 0 : 1);

        if (parentId == TournamentService.NO_PARENT) {

            if (roundPlayers == totalPlayers) { /* Only one match */
                matchService.create(totalPlayers - matchId, TournamentService.NO_PARENT, isNextMatchHome, tournamentId, playerService.findBySeed(seed, tournamentId), playerService.findBySeed(totalPlayers - seed + 1, tournamentId), standing);
                return;
            }

            matchService.createEmpty(totalPlayers - matchId, parentId, isNextMatchHome, tournamentId, standing);
            generateBracketRecursive(seed, roundPlayers * 2, matchId * 2 + 1, matchId, true, tournamentId, totalPlayers, nextStanding);
            generateBracketRecursive(roundPlayers - seed + 1, roundPlayers * 2, matchId * 2, matchId, false, tournamentId, totalPlayers, nextStanding);
            return;
        }
        if (roundPlayers == totalPlayers) {
            matchService.create(totalPlayers - matchId, totalPlayers - parentId, isNextMatchHome, tournamentId, playerService.findBySeed(seed, tournamentId), playerService.findBySeed(totalPlayers - seed + 1, tournamentId), standing);
            return;
        }

        if (roundPlayers < totalPlayers) {
            matchService.createEmpty(totalPlayers - matchId, totalPlayers - parentId, isNextMatchHome, tournamentId, standing);
            generateBracketRecursive(seed, roundPlayers * 2, matchId * 2 + 1, matchId, true, tournamentId, totalPlayers, nextStanding);
            generateBracketRecursive(roundPlayers - seed + 1, roundPlayers * 2, matchId * 2, matchId, false, tournamentId, totalPlayers, nextStanding);
        }
    }


}
