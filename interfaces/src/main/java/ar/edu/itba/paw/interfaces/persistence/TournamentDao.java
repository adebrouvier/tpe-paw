package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Tournament;

public interface TournamentDao {

    public Tournament findById (long id);

    public Tournament create(String name);
}
