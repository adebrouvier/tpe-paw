package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Standing;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;

public interface TournamentDao {

    /**
     * Finds the tournament with the specified id.
     * @param id id of the tournament.
     * @return instance of the Tournament.
     */
    public Tournament findById (long id);

    /**
     * Creates a new Tournament with the specified
     * name, game and user.
     * @param name of the Tournament.
     * @param gameId id of the Game the Tournament hosts.
     * @param userId id of the user that created the tournament.
     * @return instance of the Tournament.
     */
    public Tournament create(String name, long gameId, long userId);

    /**
     * Returns a list of featured tournaments, without instances of Player and Match loaded.
     * @param featured number of featured tournaments
     * @return a list of tournaments.
     */
    public List<Tournament> findFeaturedTournaments(int featured);

    public void setStatus(long tournamentId, Tournament.Status status);

    /**
     * @param tournamentId id of the Tournament.
     * @return List of players with their respective standing.
     */
    public List<Standing> getStandings(long tournamentId);

    /**
     * Finds the name of matching existing tournament.
     * @return the list with the names.
     */
    public List<String> findTournamentNames(String query);

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


}
