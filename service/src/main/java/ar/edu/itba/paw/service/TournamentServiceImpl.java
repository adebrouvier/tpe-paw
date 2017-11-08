package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Tournament setStatus(long tournamentId, Tournament.Status status) {
        return tournamentDao.setStatus(tournamentId, status);
    }

    @Transactional
    @Override
    public Tournament create(String name, long gameId, long userId) {
        return tournamentDao.create(name, gameId, userId);
    }

    @Override
    public List<Tournament> findFeaturedTournaments(int featured) {
        return tournamentDao.findFeaturedTournaments(featured);
    }

    @Override
    public List<String> findTournamentNames(String query) {
        return tournamentDao.findTournamentNames(query);
    }

    @Override
    public List<String> findTournamentNames(String query, long gameId) {
        return tournamentDao.findTournamentNames(query, gameId);
    }

    @Override
    public List<Tournament> findByName(String name, String game) {
        return tournamentDao.findByName(name, game);
    }

    @Override
    public Tournament getByName(String name) {
        return tournamentDao.getByName(name);
    }

    @Transactional
    @Override
    public void generateBracket(Tournament tournament){

        long tournamentId = tournament.getId();

        List<Player> players = tournament.getPlayers();
        Map<Integer, Long> seeds = new HashMap<>();

        for (Player p : players){
            seeds.put(p.getSeed(), p.getId());
        }

        int playerCount = players.size();
        int power = (int) Math.ceil((Math.log(playerCount) / Math.log(2)));
        int byes = (int) (Math.pow(2, power) - playerCount);

        for (int i = playerCount; i < playerCount + byes; i++) {
            //playerService.addToTournament(BYE_ID, tournamentId);
            Player byePlayer = new Player(BYE_NAME, tournament);
            players.add(byePlayer);
            seeds.put(i+1, (long) -1);
        }
        generateSingleEliminationBracket(tournamentId, players, seeds);
    }

    @Override
    public boolean participatesIn(long userId, long tournamentId) {
        return tournamentDao.participatesIn(userId, tournamentId);
    }

    @Override
    public Tournament getByNameAndGameId(String tournamentName, long gameId) {
        return tournamentDao.getByNameAndGameId(tournamentName, gameId);
    }

    @Override
    public List<Tournament> findTournamentByUser(long userId) {
        return tournamentDao.findTournamentByUser(userId);
    }

    @Override
    public List<Tournament> findTournamentByParticipant(long participantId) {
        return tournamentDao.findTournamentByParticipant(participantId);
    }

    /**
     * Generates every match in the Tournament bracket.
     * Setting its initial standing, calculating seeds placement
     * and generating bracket relations of every match.
     * @param tournamentId id of the tournament
     * @param players list of players
     */
    private void generateSingleEliminationBracket(long tournamentId, List<Player> players, Map<Integer, Long> seeds) {
        int totalDepth = (int) (Math.log(players.size()) / Math.log(2)); /* Size should always be a power of 2*/
        playerService.setDefaultStanding(players.size() / 2 + 1, tournamentId);
        generateBracketRecursive(TournamentService.FIRST_SEED, TournamentService.FIRST_ROUND, TournamentService.FIRST_MATCH_ID, TournamentService.NO_PARENT, TournamentService.FINAL_NEXT_MATCH_HOME, tournamentId, (int) Math.pow(2, totalDepth), TournamentService.INITIAL_STANDING, seeds);
    }

    private void generateBracketRecursive(int seed, int roundPlayers, int matchId, int parentId, boolean isNextMatchHome, long tournamentId, int totalPlayers, int standing, Map<Integer, Long> seeds) {
        if (roundPlayers > totalPlayers) {
            return;
        }

        int nextStanding = standing * 2 - (standing == 1 ? 0 : 1);

        if (parentId == TournamentService.NO_PARENT) {

            if (roundPlayers == totalPlayers) { /* Only one match */
                matchService.create(totalPlayers - matchId, TournamentService.NO_PARENT, isNextMatchHome, tournamentId, seeds.get(seed), seeds.get(totalPlayers - seed + 1), standing);
                return;
            }

            matchService.createEmpty(totalPlayers - matchId, parentId, isNextMatchHome, tournamentId, standing);
            generateBracketRecursive(seed, roundPlayers * 2, matchId * 2 + 1, matchId, true, tournamentId, totalPlayers, nextStanding, seeds);
            generateBracketRecursive(roundPlayers - seed + 1, roundPlayers * 2, matchId * 2, matchId, false, tournamentId, totalPlayers, nextStanding, seeds);
            return;
        }
        if (roundPlayers == totalPlayers) {
            matchService.create(totalPlayers - matchId, totalPlayers - parentId, isNextMatchHome, tournamentId, seeds.get(seed), seeds.get(totalPlayers - seed + 1), standing);
            return;
        }

        if (roundPlayers < totalPlayers) {
            matchService.createEmpty(totalPlayers - matchId, totalPlayers - parentId, isNextMatchHome, tournamentId, standing);
            generateBracketRecursive(seed, roundPlayers * 2, matchId * 2 + 1, matchId, true, tournamentId, totalPlayers, nextStanding, seeds);
            generateBracketRecursive(roundPlayers - seed + 1, roundPlayers * 2, matchId * 2, matchId, false, tournamentId, totalPlayers, nextStanding, seeds);
        }
    }


}
