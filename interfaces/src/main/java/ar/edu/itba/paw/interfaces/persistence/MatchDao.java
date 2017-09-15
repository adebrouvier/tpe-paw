package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Match;

import java.util.List;

public interface MatchDao {
    public static final Integer HOME = 1, AWAY = 2;
    public Match create(final int matchId, final int nextMatchId,final boolean isNextMatchHome, final long tournamentId);
    public Match create(final int matchId, final int nextMatchId,final boolean isNextMatchHome, final long tournamentId, final long localPlayerId, final long visitorPlayerId);
    public Match findById(final int id, final long tournamentId);
    public Match addPlayer(final long tournamentId, final int matchId, final long playerId, final int type);
    public Match updateScore(final long tournamentId, final int matchId,final int localScore, final int visitorScore);
    public List<Match> getTournamentMatches(long tournamentId);
}
