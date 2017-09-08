package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    PlayerDao playerDao;

    @Override
    public Player findById(long id) {
        return playerDao.findById(id);
    }

    @Override
    public Player create(String name) {
        return playerDao.create(name);
    }

    @Autowired
    private TournamentService ts;

    @Override
    public boolean addToTournament(long playerId, long tournamentId) {

        Tournament tournament = ts.findById(tournamentId);

        Player player = findById(playerId);

        //TODO: add model logic and persistence
        //tournament.addPlayer(player);

        return false;
    }


}