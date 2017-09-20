package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {

    /*TODO: check what happens with simultaneous concurrent requests*/
    private static int bracketCount = 1;

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

    private final long BYE_ID = -1;

    @Override
    public Tournament create(String name, List<Player> players) {
        Tournament tournament = tournamentDao.create(name);

        int i = 0;

        for (; i < players.size(); i++) {
            playerService.addToTournament(players.get(i).getId(), tournament.getId(), i + 1);
        }

        int power = (int) Math.ceil(Math.log(players.size()) / Math.log(2));
        int byes = (int) (Math.pow(2, power) - players.size());

        for (; i < players.size() + byes; i++) {
            playerService.addToTournament(BYE_ID, tournament.getId(), i + 1);
        }

        generateSingleEliminationBracket(tournament.getId());
        /*TODO:tournament.getMatches() is empty*/
        return tournament;
    }

    private void generateSingleEliminationBracket(long tournamentId) {
        int depth = 1;
        List<Player> players = this.findById(tournamentId).getPlayers();
        int totalDepth = (int) (Math.log(players.size()) / Math.log(2)); /* Size should always be a power of 2*/
        generateBracketRecursive(1, 2, 1, TournamentService.NO_PARENT, false, tournamentId, (int)Math.pow(2,totalDepth));
    }

    private void generateBracketRecursive(int seed, int roundPlayers, int matchId, int parentId, boolean isNextMatchHome, long tournamentId, int totalPlayers) {
        if(roundPlayers > totalPlayers) {
            return;
        }

        if(roundPlayers == totalPlayers) {
            matchService.create(matchId, parentId, isNextMatchHome, tournamentId, playerService.findBySeed(seed, tournamentId), playerService.findBySeed(totalPlayers-seed+1, tournamentId));
            matchService.updateScore(tournamentId,matchId,0,0); /*TODO: Find out a better solution*/
            return;
        }

        if(roundPlayers < totalPlayers) {
            matchService.create(matchId, parentId, isNextMatchHome, tournamentId);
            matchService.updateScore(tournamentId,matchId,0,0);
            generateBracketRecursive(seed, roundPlayers*2, matchId*2, matchId, true, tournamentId, totalPlayers);
            generateBracketRecursive(roundPlayers-seed+1, roundPlayers*2, matchId*2+1, matchId, false, tournamentId, totalPlayers);
        }
    }
    /*
    private void generateBracketRecursive(int seedHome, int seedAway, int bracketId, int parentID, boolean isNextMatchHome, long tournamentId, int depth, int totalDepth) {
        if (depth > totalDepth) {
            matchService.create(bracketId, parentID, isNextMatchHome, tournamentId, playerService.findBySeed(seedHome, tournamentId), playerService.findBySeed(seedAway, tournamentId));
            matchService.updateScore(tournamentId,bracketId,0,0); /*TODO: Find out a better solution*/
       /*     return;
        }

        matchService.create(bracketId, parentID, isNextMatchHome, tournamentId);
        matchService.updateScore(tournamentId,bracketId,0,0);
        generateBracketRecursive(seedHome, ((int) (Math.pow(2, depth))) - seedHome + 1, ++bracketCount, bracketId, true, tournamentId, depth + 1, totalDepth);
        generateBracketRecursive(seedAway, ((int) (Math.pow(2, depth))) - seedAway + 1, ++bracketCount, bracketId, false, tournamentId, depth + 1, totalDepth);
    }*/
}
