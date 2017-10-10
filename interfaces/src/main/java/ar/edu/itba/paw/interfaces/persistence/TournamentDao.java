package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Tournament;

import java.util.List;

public interface TournamentDao {

    public Tournament findById (long id);

    public Tournament create(String name, String game);

    public List<Tournament> findFeaturedTournaments();
}
