package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Standing;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;


public interface TournamentService {

    public final int NO_PARENT = 0;

    public final String BYE_NAME = "BYE";

    public final long BYE_ID = -1;

    public final int FIRST_MATCH_ID = 1;

    public final int FIRST_ROUND = 2;

    public final int FIRST_SEED = 1;

    public final boolean FINAL_NEXT_MATCH_HOME = false;

    public final int INITIAL_STANDING = 1;

    /**
     * Finds the tournament with the specified id.
     * @param id id of the tournament.
     * @return instance of the Tournament.
     */
    public Tournament findById(long id);

    /**
     * Creates a tournament with the specified name, list of players
     * and game it hosts.
     * @param name name of the tournament.
     * @param gameId id of the game that the tournament hosts.
     * @param userId id of the user that created the tournament
     * @return instance of the Tournament.
     */
    public Tournament create(String name, long gameId, long userId);

    /**
     * Returns a list of featured tournaments, without instances of Player and Match loaded.
     * @param featured number of tournaments
     * @return a list of tournaments.
     */
    public List<Tournament> findFeaturedTournaments(int featured);

    /**
     * Changes the status of the tournament
     * @param tournamentId id of the tournament.
     * @param status new status
     */
    public void setStatus(long tournamentId, Tournament.Status status);

    /**
     * @param tournamentId id of the Tournament.
     * @return List of players with their respective standing.
     */
    public List<Standing> getStandings(long tournamentId);

    /**
     * Finds the name of a matching existing tournament
     * @param query term to be searched
     * @return the list with the names
     */
    public List<String> findTournamentNames(String query);

    /**
     * Finds the name of a matching existing tournament
     * @param query term to be searched
     * @param gameId of each tournament
     * @return the list with the names
     */
    public List<String> findTournamentNames(String query, long gameId);

    /**
     * Finds a tournament with the specified name.
     * @param name the name of part of the name of the tournament.
     * @return a list of tournaments.
     */
    public List<Tournament> findByName(String name);

    /**
     * Find Tournament with specified name.
     * @param name of the Tournament.
     * @return instance of the Tournament.
     */
    public Tournament getByName(String name);

    /**
     * Generates bracket for the specified Tournament
     * @param tournamentId id of the Tournament
     */
    public void generateBracket(long tournamentId);
}
