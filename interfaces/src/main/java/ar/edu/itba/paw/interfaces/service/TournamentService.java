package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Standing;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;

public interface TournamentService {

    public final int NO_PARENT = 0;

    public final String BYE_NAME = "BYE";

    public final long BYE_ID = -1;

    /**
     * Finds the tournament with the specified id
     * @param id id of the tournament
     * @return instance of the Tournament
     */
    public Tournament findById(long id);

    /**
     * Creates a tournament with the specified name and list of players
     * @param name name of the tournament
     * @param players list of players
     * @return instance of the Tournament
     */
    public Tournament create(String name, List<Player> players, String game);


    public Tournament create(String name, List<Player> players, String game,int tier);

    /**
     * Returns a list of featured tournaments, without instances of Player and Match loaded
     * @return a list of tournaments
     */
    public List<Tournament> findFeaturedTournaments();

    public void endTournament(long tournamentId);

    public List<Standing> getStandings(long tournamentId);

    /**
     * Finds the name of every existing tournament
     * @return the list with the names
     */
    public List<String> findTournamentNames();

    /**
     * Finds a tournament with the specified name
     * @param name the name of part of the name of the tournament
     * @return a list of tournaments
     */
    public List<Tournament> findByName(String name);
}
