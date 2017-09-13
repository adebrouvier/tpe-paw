package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Match;

public interface MatchService {
    public static final Integer LOCAL = 1, VISITOR = 2;
    public Match create(final Integer matchId, final Integer nextMatchId, final long tournamentId);
    public Match create(final Integer matchId,final Integer nextMatchId, final long tournamentId, final long localPlayerId, final long visitorPlayerId);
    public Match findById(final Integer id, final long tournamentId);
    public Match addPlayer(final long tournamentId, final long matchId, final long playerId, final Integer type);
    public Match updateScore(final long tournamentId, final long matchId, final Integer localScore, final Integer visitorScore);
}
