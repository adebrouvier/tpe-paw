package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Match;

public interface MatchService {
    public static final Integer HOME = 1, AWAY = 2;
    public Match create(final int matchId, final int nextMatchId,final boolean isNextMatchHome, final long tournamentId);
    public Match create(final int matchId,final int nextMatchId,final boolean isNextMatchHome, final long tournamentId, final long homePlayerId, final long awayPlayerId);
    public Match createWinnerMatch(final int matchId, final int nextMatchId,final int loserMatchId, final boolean isNextMatchHome, final long tournamentId);
    public Match createWinnerMatch(final int matchId, final int nextMatchId,final int loserMatchId, final boolean isNextMatchHome, final long tournamentId, final long homePlayerId, final long awayPlayerId);
    public Match createLoserMatch(final int matchId, final int nextMatchId, final boolean isNextMatchHome, final long tournamentId);
    public Match findById(final int id, final long tournamentId);
    public Match addPlayer(final long tournamentId, final int matchId, final long playerId, final int type);
    public Match updateScore(final long tournamentId, final int matchId,final int homeScore, final int awayScore);
}
