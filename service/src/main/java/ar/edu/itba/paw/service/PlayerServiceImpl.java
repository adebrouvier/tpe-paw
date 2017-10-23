package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerDao playerDao;

    @Override
    public void delete(long id) {
        playerDao.delete(id);
    }

    @Override
    public boolean removeToTournament(long tournamentId, long playerId) {
        return playerDao.removeToTournament(tournamentId, playerId);
    }

    @Override
    public boolean changeSeedToTournament(long tournamentId, int playerOldSeed, int playerNewSeed) {
        return playerDao.changeSeedToTournament(tournamentId, playerOldSeed, playerNewSeed);
    }

    @Override
    public Player findById(long id) {
        return playerDao.findById(id);
    }

    @Override
    public long findBySeed(int seed, long tournamentId) {
        return playerDao.findBySeed(seed,tournamentId);
    }

    @Override
    public Player create(String name) {
        return playerDao.create(name);
    }

    @Override
    public Player create(String name, long userId) {
        return playerDao.create(name, userId);
    }

    @Autowired
    private TournamentService ts;

    @Override
    public boolean addToTournament(long playerId, long tournamentId,int seed) {
        return playerDao.addToTournament(playerId, tournamentId,seed);
    }

    @Override
    public void setDefaultStanding(int standing, long tournamentId) {
        playerDao.setDefaultStanding(standing, tournamentId);
    }

    @Override
    public List<Player> getTournamentPlayers(long tournamentId){
        return playerDao.getTournamentPlayers(tournamentId);
    }

    @Override
    public void addToTournament(long playerId, long tournamentId) {
        playerDao.addToTournament(playerId, tournamentId);
    }

}
