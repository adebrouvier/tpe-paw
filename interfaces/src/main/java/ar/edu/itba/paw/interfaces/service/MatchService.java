package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Match;

public interface MatchService {
    public static final int BYE_WIN_SCORE = 1;

    /**
     * Creates a match with no defined participants
     * @param matchId id of the match
     * @param nextMatchId id of the next match
     * @param isNextMatchHome true if the player should be home in the next round, false otherwise
     * @param tournamentId id of the tournament that match belongs to
     * @return the created match
     */
    public Match createEmpty(final int matchId, final int nextMatchId, final boolean isNextMatchHome, final long tournamentId);

    /**
     * Creates a match between two players
     * @param matchId id of the match
     * @param nextMatchId id of the next match
     * @param isNextMatchHome true if the player should be home in the next round, false otherwise
     * @param tournamentId id of the tournament that match belongs to
     * @param homePlayerId id of the home player
     * @param awayPlayerId id of the away player
     * @return the created match
     */
    public Match create(final int matchId,final int nextMatchId,final boolean isNextMatchHome, final long tournamentId, final long homePlayerId, final long awayPlayerId);

    /**
     * Finds the Match with the specified id
     * @param id id of the match
     * @param tournamentId id of the tournament that the match belongs to
     * @return the Match instance, or null of it doesn't exist
     */
    public Match findById(final int id, final long tournamentId);
    public Match updateScore(final long tournamentId, final int matchId,final int homeScore, final int awayScore);
}
