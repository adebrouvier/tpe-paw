package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Tournament;

public interface TournamentService {

    public Tournament findById(long id);

    public Tournament create(String name, String players);

}
