package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Inscription;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface InscriptionService {

    /**
     * Creates an inscription for a tournament
     * @param user the user that wants to join
     * @param tournament the tournament that the user wants to join
     * @return the created inscription
     */
    Inscription create(User user, Tournament tournament);

    /**
     * Finds an inscription by an user in a tournament
     * @param userId the id of the user
     * @param tournamentId the id of the tournament
     * @return the inscription or null if not found
     */
    Inscription findByIds(long userId, long tournamentId);

    /**
     * Finds all pending inscriptions to a tournament
     * @param tournamentId the id of the tournament
     * @return the list of inscriptions
     */
    List<Inscription> finByTournamentId(long tournamentId);

    /**
     * Deletes an user from the inscription list
     * @param tournamentId id of the tournament
     * @param userId id of the user
     */
    void delete(long tournamentId, long userId);
}
