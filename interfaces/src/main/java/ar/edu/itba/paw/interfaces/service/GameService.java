package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Game;

import java.util.List;

public interface GameService {

    /**
     * @param query term to be search
     * @return every matching Game by name that was not
     * user generated.
     */
    public List<String> findGameNames(String query);

    /**
     * Finds the game with the specified id.
     * @param id id of the Game.
     * @return instance of the Game.
     */
    public Game findById(final long id);
}
