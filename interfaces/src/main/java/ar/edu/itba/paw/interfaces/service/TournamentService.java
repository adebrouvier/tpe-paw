package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Comment;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;


public interface TournamentService {

    int NO_PARENT = 0;

    String BYE_NAME = "BYE";

    long BYE_ID = -1;

    int FIRST_MATCH_ID = 1;

    int FIRST_ROUND = 2;

    int FIRST_SEED = 1;

    boolean FINAL_NEXT_MATCH_HOME = false;

    int INITIAL_STANDING = 1;

    /**
     * Finds the tournament with the specified id.
     * @param id id of the tournament.
     * @return instance of the Tournament.
     */
    Tournament findById(long id);

    /**
     * Creates a tournament with the specified name, list of players
     * and game it hosts.
     * @param name name of the tournament.
     * @param gameId id of the game that the tournament hosts.
     * @param userId id of the user that created the tournament
     * @return instance of the Tournament.
     */
    Tournament create(String name, long gameId, long userId);

    /**
     * Returns a list of featured tournaments, without instances of Player and Match loaded.
     * @param featured number of tournaments
     * @return a list of tournaments.
     */
    List<Tournament> findFeaturedTournaments(int featured);

    /**
     * Changes the status of the tournament
     * @param tournamentId id of the tournament.
     * @param status new status
     */
    Tournament setStatus(long tournamentId, Tournament.Status status);

    /**
     * Finds the name of a matching existing tournament
     * @param query term to be searched
     * @return the list with the names
     */
    List<String> findTournamentNames(String query);

    /**
     * Finds the name of a matching existing tournament
     * @param query term to be searched
     * @param gameId of each tournament
     * @return the list with the names
     */
    List<String> findTournamentNames(String query, long gameId);

    /**
     * Finds a tournament with the specified name.
     * @param name the name of part of the name of the tournament.
     * @return a list of tournaments.
     */
    List<Tournament> findByName(String name, String game);

    /**
     * Find Tournament with specified name.
     * @param name of the Tournament.
     * @return instance of the Tournament.
     */
    Tournament getByName(String name);

    /**
     * Generates bracket for the specified Tournament
     * @param tournament Tournament where the bracket will be generated
     */
    void generateBracket(Tournament tournament);

    /**
     * Checks if an user is already participating in a Tournament.
     * @param userId id of the User.
     * @param tournamentId id of the Tournament.
     * @return true if the user is participating in the tournament,
     * false otherwise.
     */
    boolean participatesIn(long userId, long tournamentId);

    /**
     * Find finished tournament by name and gameId.
     * @param tournamentName the tournament name.
     * @param gameId id of the game.
     * @return a tournament.
     */
    Tournament getByNameAndGameId(String tournamentName, long gameId);

    List<Tournament> findTournamentByUser(long userId, int page);

    List<Tournament> findTournamentByParticipant(long participantId, int page);

    void addComment(long tournamentId, Comment comment);

    void addReply(long tournamentId, Comment reply, long parentId);
}
