package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;

public interface TournamentService {

    public Tournament findById(long id);

    public Tournament create(String name, int maxParticipants, int cantParticipants, List<Player> players);

}
