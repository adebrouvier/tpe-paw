package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Match;

import java.util.List;

public interface MatchDao {
    public Match createEmpty(final int matchId, final int nextMatchId, final boolean isNextMatchHome, final long tournamentId, int standing);
    public Match create(final int matchId, final int nextMatchId,final boolean isNextMatchHome, final long tournamentId, final long localPlayerId, final long visitorPlayerId, int standing);
    public Match findById(final int id, final long tournamentId);
    public Match updateScore(final long tournamentId, final int matchId,final int localScore, final int visitorScore);
    public List<Match> getTournamentMatches(long tournamentId);
}
