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
    public void endTournament(long tournamentId) {
        tournamentDao.endTournament(tournamentId);
    }

    @Override
    public Tournament create(String name, List<Player> players, String game) {
        Tournament tournament = tournamentDao.create(name, game);
        Map<Integer,Long> seeds = new HashMap<>();

        int i = 0;
        int playerCount = players.size();

        for (; i < playerCount; i++) {
            long playerId = players.get(i).getId();
            int seed = i + 1;
            playerService.addToTournament(playerId, tournament.getId(), seed);
            seeds.put(seed, playerId);
        }

        int power = (int) Math.ceil(Math.log(players.size()) / Math.log(2));
        int byes = (int) (Math.pow(2, power) - players.size());

        for (; i < playerCount + byes; i++) {
            final int seed = i + 1;
            playerService.addToTournament(BYE_ID, tournament.getId(), seed);
            players.add(new Player(BYE_NAME, BYE_ID));
            seeds.put(seed, BYE_ID);
        }

        generateSingleEliminationBracket(tournament.getId(), players, seeds);
        /*TODO:tournament.getMatches() is empty*/
        return tournament;
    }

    @Override
    public Tournament create(String name, List<Player> players, String game, int tier) {
        Tournament tournament = tournamentDao.create(name, game, tier);
        Map<Integer,Long> seeds = new HashMap<>();

        int i = 0;
        int playerCount = players.size();

        for (; i < playerCount; i++) {
            long playerId = players.get(i).getId();
            int seed = i + 1;
            playerService.addToTournament(playerId, tournament.getId(), seed);
            seeds.put(seed, playerId);
        }

        int power = (int) Math.ceil(Math.log(players.size()) / Math.log(2));
        int byes = (int) (Math.pow(2, power) - players.size());

        for (; i < playerCount + byes; i++) {
            final int seed = i + 1;
            playerService.addToTournament(BYE_ID, tournament.getId(), seed);
            players.add(new Player(BYE_NAME, BYE_ID));
            seeds.put(seed, BYE_ID);
        }

        generateSingleEliminationBracket(tournament.getId(), players, seeds);
        /*TODO:tournament.getMatches() is empty*/
        tournament = tournamentDao.findById(tournament.getId());
        return tournament;
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
    public List<String> findTournamentNames() {
        return tournamentDao.findTournamentNames();
    }

    @Override
    public List<Tournament> findByName(String name) {
        return tournamentDao.findByName(name);
    }

    private void generateSingleEliminationBracket(long tournamentId, List<Player> players, Map<Integer, Long> seeds) {
        int totalDepth = (int) (Math.log(players.size()) / Math.log(2)); /* Size should always be a power of 2*/
        int initialStanding = 1;
        playerService.setDefaultStanding(players.size()/2 + 1, tournamentId);
        generateBracketRecursive(1, 2, 1, TournamentService.NO_PARENT, false, tournamentId, (int)Math.pow(2,totalDepth), initialStanding, seeds);
    }

    private void generateBracketRecursive(int seed, int roundPlayers, int matchId, int parentId, boolean isNextMatchHome, long tournamentId, int totalPlayers, int standing, Map<Integer, Long> seeds) {
        if(roundPlayers > totalPlayers) {
            return;
        }

        int nextStanding  = standing * 2 - (standing == 1 ? 0 : 1);

        if(parentId == TournamentService.NO_PARENT) {

            if (roundPlayers == totalPlayers){ /* Only one match */
                matchService.create(totalPlayers-matchId, TournamentService.NO_PARENT, isNextMatchHome, tournamentId, seeds.get(seed), seeds.get(totalPlayers-seed+1), standing);
                return;
            }

            matchService.createEmpty(totalPlayers-matchId, parentId, isNextMatchHome, tournamentId, standing);
            generateBracketRecursive(seed, roundPlayers*2, matchId*2+1, matchId, true, tournamentId, totalPlayers, nextStanding, seeds);
            generateBracketRecursive(roundPlayers-seed+1, roundPlayers*2, matchId*2, matchId, false, tournamentId, totalPlayers, nextStanding, seeds);
            return;
        }
        if(roundPlayers == totalPlayers) {
            matchService.create(totalPlayers-matchId, totalPlayers-parentId, isNextMatchHome, tournamentId, seeds.get(seed), seeds.get(totalPlayers-seed+1), standing);
            return;
        }

        if(roundPlayers < totalPlayers) {
            matchService.createEmpty(totalPlayers-matchId, totalPlayers-parentId, isNextMatchHome, tournamentId, standing);
            generateBracketRecursive(seed, roundPlayers*2, matchId*2+1, matchId, true, tournamentId, totalPlayers, nextStanding, seeds);
            generateBracketRecursive(roundPlayers-seed+1, roundPlayers*2, matchId*2, matchId, false, tournamentId, totalPlayers, nextStanding, seeds);
        }
    }
}
