package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Game;

import java.util.List;

public interface GameService {
    public List<String> findGamesName();
    public Game findById(final long id);
}
