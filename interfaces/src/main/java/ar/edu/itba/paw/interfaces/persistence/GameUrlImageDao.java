package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Game;

public interface GameUrlImageDao {

    /**
     * Create a url that locate the game image
     *
     * @param game id of the Game
     * @param url  the url of the image
     */
    void create(Game game, String url);

    /**
     * Find the url that locate the game image
     *
     * @param gameId id of the Game
     * @return the url of the image
     */
    String findById(long gameId);
}
