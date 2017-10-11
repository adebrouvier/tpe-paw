package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Game;

import java.util.List;

public interface GameDao {

    public List<String> findGamesName();
    public Game findById(final long id);
    public Game findByName(final String name);
    public Game create(final String name, final boolean userGenerated);

}
