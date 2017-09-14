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

    private static int bracketCount = 1;

    @Autowired
    TournamentDao tournamentDao;

    @Autowired
    PlayerService playerService;

    @Autowired
    MatchService matchService;


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
        int totalDepth = (int) (Math.log(players.size())/Math.log(2));/*Size should always be a power of 2*/
        generateBracketRecursive(1,2, 1,0,tournamentId,this.findById(tournamentId).getPlayers(),depth,totalDepth);
    }

    private void generateBracketRecursive(int seedHome, int seedAway, int id , int idPadre, long tournamentId, List<Player> players, int depth, int totalDepth){
        if(depth>totalDepth){
            matchService.create(id,idPadre,tournamentId, players.get(seedHome-1).getId(),players.get(seedAway-1).getId());
            return;
        }
        matchService.create(id,idPadre,tournamentId);
        generateBracketRecursive(seedHome, ((int) (Math.pow(2,depth)))-seedHome,++bracketCount,id,tournamentId,players,depth+1, totalDepth);
        generateBracketRecursive(seedAway, ((int) (Math.pow(2,depth)))-seedAway,++bracketCount,id,tournamentId,players,depth+1, totalDepth);
    }
}
