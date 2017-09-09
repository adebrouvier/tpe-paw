package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Match;

public interface MatchDao {
    public static final Integer LOCAL = 1, VISITOR = 2;
    public Match create(final Integer name, final long tournamentId);
    public Match findByName(final Integer name, final long tournamentId);
    public boolean addPlayer(final long tournamentId, final long matchId, final long playerId, final Integer type);
}
