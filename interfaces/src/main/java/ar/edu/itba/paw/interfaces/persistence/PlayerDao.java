package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;

public interface PlayerDao {

    int NAME_LENGTH = 25;

    int EMPTY = 0;

    int EMPTY_STANDING = 0;

    int SUCCESSFUL_INSERT = 1;

    /**
     * Delete a player with a specified id.
     *
     * @param id id of the player.
     */
    void delete(long id);

    /**
     * Remove player to a tournament.
     *
     * @param tournamentId id of the tournament.
     * @param playerId     id of the player.
     * @return if can remove player to tournament.
     */
    boolean removeFromTournament(long tournamentId, long playerId);

    /**
     * Change player seed.
     *
     * @param tournamentId  id of the tournament.
     * @param playerOldSeed the old seed of the player.
     * @param playerNewSeed the new seed of the player.
     * @return if can change player seed.
     */
    boolean changeSeed(long tournamentId, int playerOldSeed, int playerNewSeed);

    /**
     * Finds a player with a specified id.
     *
     * @param id id of the player.
     * @return an instance of the player.
     */
    Player findById(long id);

    /**
     * Finds the id of the player with an specific seed.
     *
     * @param seed         required seed.
     * @param tournamentId id of the desired tournament.
     * @return id of the player.
     */
    long findBySeed(int seed, long tournamentId);

    /**
     * Creates a player.
     *
     * @param name name of the player.
     * @return an instance of the player.
     */
    Player create(String name, Tournament tournament);

    /**
     * Sets every players standing in the tournament to its
     * starting one.
     *
     * @param standing     default for the start of the tournament.
     * @param tournamentId id of the tournament.
     */
    void setDefaultStanding(int standing, long tournamentId);

    /**
     * Creates a Player with the specified name
     * and user id.
     *
     * @param name of the Player
     * @param user id of the User
     * @return an instance of the new Player
     */
    Player create(String name, User user, Tournament tournament);

    /**
     * Adds a Player to a Tournament
     * both specified by id
     *
     * @param playerId     id of the Player
     * @param tournamentId id of the Tournament
     */
    void addToTournament(long playerId, long tournamentId);
}
