package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.GameImage;

public interface GameImageDao {
    public GameImage findById(long gameId);
}
