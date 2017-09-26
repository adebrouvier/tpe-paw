package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Match;

public interface MatchService {
    public static final Integer HOME = 1, AWAY = 2;
    public static final int BYE_WIN_SCORE = 1;
    public Match create(final int matchId, final int nextMatchId,final boolean isNextMatchHome, final long tournamentId);
    public Match create(final int matchId,final int nextMatchId,final boolean isNextMatchHome, final long tournamentId, final long localPlayerId, final long visitorPlayerId);
    public Match findById(final int id, final long tournamentId);
    public Match addPlayer(final long tournamentId, final int matchId, final long playerId, final int type);
    public Match updateScore(final long tournamentId, final int matchId,final int homeScore, final int awayScore);
}
