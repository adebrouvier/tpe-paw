package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Game;

import java.util.List;

public interface GameDao {

    /**
     * @return every Game by name that was not
     * user generated.
     */
    public List<String> findGamesName();
    /**
     * Finds the game with the specified id.
     * @param id id of the Game.
     * @return instance of the Game.
     */
    public Game findById(final long id);

    /**
     * Finds game with the specified name.
     * @param name of the Game.
     * @return instance of the Game.
     */
    public Game findByName(final String name);

    /**
     * Create new Game with specified name.
     * It also adds if it was user generated or
     * not.
     * @param name of the game.
     * @param userGenerated if it was created by the user
     *                      or not.
     * @return instance of the Game.
     */
    public Game create(final String name, final boolean userGenerated);

}
