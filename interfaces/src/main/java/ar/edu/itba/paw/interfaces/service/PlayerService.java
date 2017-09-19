package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Player;

public interface PlayerService {

    public Player findById(long id);

    public long findBySeed(int seed, long tournamentId);

    public Player create(String name);

    public boolean addToTournament(long playerId,long tournamentId, int seed);

}
