package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.GameImage;

public interface GameImageService {

    /**
     * Find an image of specified game
     * @param gameId id of the Game
     * @return a object that contain the image
     */
    public GameImage findById(long gameId);
}
