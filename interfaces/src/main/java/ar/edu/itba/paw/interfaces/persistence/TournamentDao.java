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
     * name and game.
     * @param name of the Tournament.
     * @param game name of the Game the Tournament hosts.
     * @return instance of the Tournament.
     */
    public Tournament create(String name, String game);

    /**
     * Creates a new Tournament with the specified
     * name, game and tier.
     * @param name of the Tournament.
     * @param game name of the Game the Tournament hosts.
     * @param tier corresponding to the Tournament's relevancy.
     * @return instance of the Tournament.
     */
    public Tournament create(String name, String game,int tier);

    /**
     * Returns a list of featured tournaments, without instances of Player and Match loaded.
     * @return a list of tournaments.
     */
    public List<Tournament> findFeaturedTournaments();

    /**
     * Concludes the Tournament with the specified id
     * preventing further editing of said Tournament.
     * @param tournamentId id of the tournament.
     */
    public void endTournament(long tournamentId);

    /**
     * @param tournamentId id of the Tournament.
     * @return List of players with their respective standing.
     */
    public List<Standing> getStandings(long tournamentId);

    /**
     * Finds the name of every existing tournament.
     * @return the list with the names.
     */
    public List<String> findTournamentNames();

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
