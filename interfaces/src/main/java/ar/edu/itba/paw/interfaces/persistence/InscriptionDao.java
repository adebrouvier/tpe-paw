package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Inscription;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface InscriptionDao {

    Inscription create(User user, Tournament tournament);

    Inscription findByIds(long loggedUserId, long tournamentId);

    List<Inscription> findByTournamentId(long tournamentId);

    void delete(long tournamentId, long userId);
}
