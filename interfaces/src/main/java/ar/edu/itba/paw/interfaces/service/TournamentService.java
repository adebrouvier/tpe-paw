package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;

public interface TournamentService {

    public final int NO_PARENT = 0;

    public Tournament findById(long id);

    public Tournament createSingleEliminationBracket(String name, List<Player> players);

}
