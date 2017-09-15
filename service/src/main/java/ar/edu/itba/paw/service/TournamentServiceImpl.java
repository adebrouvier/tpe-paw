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
public class TournamentServiceImpl implements TournamentService{

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

    @Override
    public Tournament create(String name, int maxParticipants, int cantParticipants, List<Player> players) {
        Tournament tournament = tournamentDao.create(name, maxParticipants, cantParticipants);
        for (Player player:players) {
            playerService.addToTournament(player.getId(),tournament.getId(),0); /*TODO: Remove position*/
        }
        generateBracket(tournament.getId());
        return tournament;
    }

    private void generateBracket(long tournamentId){
        int depth = 1;
        List<Player> players = this.findById(tournamentId).getPlayers();
        int totalDepth = (int) (Math.log(players.size())/Math.log(2)) ; /* Size should always be a power of 2*/
        generateBracketRecursive(1,2, 1,TournamentService.NO_PARENT,false,tournamentId,this.findById(tournamentId).getPlayers(),depth+1,totalDepth);
    }

    private void generateBracketRecursive(int seedHome, int seedAway, int bracketId , int parentID,boolean isNextMatchHome, long tournamentId, List<Player> players, int depth, int totalDepth){
        if(depth > totalDepth){
            matchService.create(bracketId,parentID,isNextMatchHome,tournamentId, players.get(seedHome-1).getId(),players.get(seedAway-1).getId());
            return;
        }

        matchService.create(bracketId,parentID,isNextMatchHome,tournamentId);
        generateBracketRecursive(seedHome, ((int) (Math.pow(2,depth)))-seedHome+1,++bracketCount,bracketId,true,tournamentId,players,depth+1, totalDepth);
        generateBracketRecursive(seedAway, ((int) (Math.pow(2,depth)))-seedAway+1,++bracketCount,bracketId,false,tournamentId,players,depth+1, totalDepth);
    }
}
