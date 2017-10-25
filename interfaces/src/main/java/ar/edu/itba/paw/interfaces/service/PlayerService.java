package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Player;

import java.util.List;

public interface PlayerService {

    /**
     * Delete a player with a specified id.
     * @param id id of the player.
     */
    public void delete(long id);

    /**
     * Remove player to a tournament.
     * @param tournamentId id of the tournament.
     * @param playerId id of the player.
     * @return if can remove player to tournament.
     */
    public boolean removeToTournament(long tournamentId, long playerId);

    /**
     * Change player seed.
     * @param tournamentId id of the tournament.
     * @param playerOldSeed the old seed of the player.
     * @param playerNewSeed the new seed of the player.
     * @return if can change player seed.
     */
    public boolean changeSeedToTournament(long tournamentId, int playerOldSeed, int playerNewSeed);

    /**
     * Finds a player with a specified id.
     * @param id id of the player.
     * @return an instance of the player.
     */
    public Player findById(long id);

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
     * Creates a player linked to a user
     *
     * @param name   of the player
     * @param userId id of the user
     * @return an instance of the player
     */
    public Player create(String name, long userId);

    /**
     * Adds a player to a tournament.
     * @param playerId id of the player.
     * @param tournamentId id of the tournament.
     * @param seed seed of the player.
     * @return true if it can be added, false otherwise.
     */
    public boolean addToTournament(long playerId, long tournamentId, int seed);

    /**
     * Sets every players standing in the tournament to its
     * starting one.
     *
     * @param standing     default for the start of the tournament.
     * @param tournamentId id of the tournament.
     */
    void setDefaultStanding(int standing, long tournamentId);

    /**
     * Gets every Player that participates
     * in the Tournament specified with the id
     * @param tournamentId id of the Tournament
     * @return list of the Players participating
     */
    public List<Player> getTournamentPlayers(long tournamentId);

    /**
     * Adds Player to a Tournament both specified
     * by id
     * @param id of the Player
     * @param tournamentId id of the Tournament
     */
    void addToTournament(long id, long tournamentId);
}
