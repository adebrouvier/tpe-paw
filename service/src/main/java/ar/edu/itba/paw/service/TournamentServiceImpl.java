package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Tournament> findAllTournaments() {
        return tournamentDao.findAllTournaments();
    }

    private void generateSingleEliminationBracket(long tournamentId) {
        List<Player> players = this.findById(tournamentId).getPlayers();
        int totalDepth = (int) (Math.log(players.size()) / Math.log(2)); /* Size should always be a power of 2*/
        generateBracketRecursive(1, 2, 1, TournamentService.NO_PARENT, false, tournamentId, (int)Math.pow(2,totalDepth));
    }

    private void generateBracketRecursive(int seed, int roundPlayers, int matchId, int parentId, boolean isNextMatchHome, long tournamentId, int totalPlayers) {
        if(roundPlayers > totalPlayers) {
            return;
        }

        if(parentId == TournamentService.NO_PARENT) {

            if (roundPlayers == totalPlayers){ /* Only one match */
                matchService.create(totalPlayers-matchId, TournamentService.NO_PARENT, isNextMatchHome, tournamentId, playerService.findBySeed(seed, tournamentId), playerService.findBySeed(totalPlayers-seed+1, tournamentId));
                return;
            }

            matchService.createEmpty(totalPlayers-matchId, parentId, isNextMatchHome, tournamentId);
            generateBracketRecursive(seed, roundPlayers*2, matchId*2+1, matchId, true, tournamentId, totalPlayers);
            generateBracketRecursive(roundPlayers-seed+1, roundPlayers*2, matchId*2, matchId, false, tournamentId, totalPlayers);
            return;
        }
        if(roundPlayers == totalPlayers) {
            matchService.create(totalPlayers-matchId, totalPlayers-parentId, isNextMatchHome, tournamentId, playerService.findBySeed(seed, tournamentId), playerService.findBySeed(totalPlayers-seed+1, tournamentId));
            return;
        }

        if(roundPlayers < totalPlayers) {
            matchService.createEmpty(totalPlayers-matchId, totalPlayers-parentId, isNextMatchHome, tournamentId);
            generateBracketRecursive(seed, roundPlayers*2, matchId*2+1, matchId, true, tournamentId, totalPlayers);
            generateBracketRecursive(roundPlayers-seed+1, roundPlayers*2, matchId*2, matchId, false, tournamentId, totalPlayers);
        }
    }
}
