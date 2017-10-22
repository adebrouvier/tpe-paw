package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Player;

import java.util.List;

public interface PlayerDao {

    public final int NAME_LENGTH = 25;

    public final int EMPTY = 0;

    public final int EMPTY_STANDING = 0;

    public final int SUCCESSFUL_INSERT = 1;

    /**
     * Finds a player with a specified id.
     * @param id id of the player.
     * @return an instance of the player.
     */
    public Player findById (long id);

    /**
     * Finds the id of the player with an specific seed.
     * @param seed required seed.
     * @param tournamentId id of the desired tournament.
     * @return id of the player.
     */
    public long findBySeed(int seed, long tournamentId);

    /**
     * Creates a player.
     * @param name name of the player.
     * @return an instance of the player.
     */
    public Player create(String name);

    /**
     * Adds a player to a tournament.
     * @param playerId id of the player.
     * @param tournamentId id of the tournament.
     * @param seed seed of the player.
     * @return true if it can be added, false otherwise.
     */
    public boolean addToTournament(long playerId,long tournamentId, int seed);

    /**
     * Find every Player that participates in
     * the specified Tournament.
     * @param tournamentId id of the Tournament.
     * @return list of the Players participating
     * in the Tournament.
     */
    public List<Player> getTournamentPlayers(long tournamentId);

    /**
     * Sets every players standing in the tournament to its
     * starting one.
     * @param standing default for the start of the tournament.
     * @param tournamentId id of the tournament.
     */
    void setDefaultStanding(int standing, long tournamentId);

    public Player create(String name, long userId);

    public void addToTournament(long playerId, long tournamentId);

    public void removeFromTournament(long playerId, long tournamentId);
}
