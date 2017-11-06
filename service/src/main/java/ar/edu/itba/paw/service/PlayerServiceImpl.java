package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerDao playerDao;

    @Transactional
    @Override
    public void delete(long id) {
        playerDao.delete(id);
    }

    @Transactional
    @Override
    public boolean removeFromTournament(long tournamentId, long playerId) {
        return playerDao.removeFromTournament(tournamentId, playerId);
    }

    @Transactional
    @Override
    public boolean changeSeed(long tournamentId, int playerOldSeed, int playerNewSeed) {
        return playerDao.changeSeed(tournamentId, playerOldSeed, playerNewSeed);
    }

    @Override
    public Player findById(long id) {
        return playerDao.findById(id);
    }

    @Override
    public long findBySeed(int seed, long tournamentId) {
        return playerDao.findBySeed(seed,tournamentId);
    }

    @Transactional
    @Override
    public Player create(String name, Tournament tournament) {
        return playerDao.create(name, tournament);
    }

    @Transactional
    @Override
    public Player create(String name, User user, Tournament tournament) {
        return playerDao.create(name, user, tournament);
    }

    @Transactional
    @Override
    public void setDefaultStanding(int standing, long tournamentId) {
        playerDao.setDefaultStanding(standing, tournamentId);
    }

    @Override
    public List<Player> getTournamentPlayers(long tournamentId){
        return playerDao.getTournamentPlayers(tournamentId);
    }

    @Transactional
    @Override
    public void addToTournament(long playerId, long tournamentId) {
        playerDao.addToTournament(playerId, tournamentId);
    }

}
