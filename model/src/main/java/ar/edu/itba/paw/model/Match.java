package ar.edu.itba.paw.model;

public class Match {

    private long homePlayerId;
    private long awayPlayerId;
    private int homePlayerScore;
    private int awayPlayerScore;
    private long matchId;
    private long nextMatchId;
    private long tournamentId;

    public Match(long homePlayerId, long awayPlayerId, long matchId, long nextMatchId, long tournamentId) {
        this.homePlayerId = homePlayerId;
        this.awayPlayerId = awayPlayerId;
        this.matchId = matchId;
        this.nextMatchId = nextMatchId;
        this.tournamentId = tournamentId;
    }

    public Match(int matchId, int nextMatchId, long tournamentId) {
        this.matchId = matchId;
        this.nextMatchId = nextMatchId;
        this.tournamentId = tournamentId;
    }

    public Match(long homePlayerId, long awayPlayerId, int homePlayerScore, int awayPlayerScore, long matchId, int nextMatchId, long tournamentId) {
        this.homePlayerId = homePlayerId;
        this.awayPlayerId = awayPlayerId;
        this.homePlayerScore = homePlayerScore;
        this.awayPlayerScore = awayPlayerScore;
        this.matchId = matchId;
        this.nextMatchId = nextMatchId;
        this.tournamentId = tournamentId;
    }

    public long getHomePlayerId() {
        return homePlayerId;
    }

    public long getAwayPlayerId() {
        return awayPlayerId;
    }

    public int getHomePlayerScore() {
        return homePlayerScore;
    }

    public int getAwayPlayerScore() {
        return awayPlayerScore;
    }

    public long getMatchId() {
        return matchId;
    }

    public long getNextMatchId() {
        return nextMatchId;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (matchId != match.matchId) return false;
        return tournamentId == match.tournamentId;
    }
}
