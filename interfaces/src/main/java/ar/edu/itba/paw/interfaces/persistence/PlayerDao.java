package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Player;

import java.util.List;

public interface PlayerDao {

    public Player findById (long id);

    public long findBySeed(int seed, long tournamentId);

    public Player create(String name);

    public boolean addToTournament(long playerId,long tournamentId, int seed);

    public List<Player> getTournamentPlayers(long tournamentId);
}
