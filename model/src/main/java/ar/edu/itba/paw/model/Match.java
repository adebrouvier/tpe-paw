package ar.edu.itba.paw.model;

public class Match {

    private long homePlayerId;
    private long awayPlayerId;
    private Player homePlayer;
    private Player awayPlayer;
    private int homePlayerScore;
    private int awayPlayerScore;
    private long id;
    private long nextMatchId;
    private long tournamentId;
    private boolean isNextMatchHome;
    private int standing;

    public Match(long homePlayerId, long awayPlayerId, long id, long nextMatchId,boolean isNextMatchHome, long tournamentId, int standing) {
        this.homePlayerId = homePlayerId;
        this.awayPlayerId = awayPlayerId;
        this.id = id;
        this.nextMatchId = nextMatchId;
        this.tournamentId = tournamentId;
        this.isNextMatchHome = isNextMatchHome;
        this.standing = standing;
    }

    public Match(int id, int nextMatchId,boolean isNextMatchHome, long tournamentId, int standing) {
        this.id = id;
        this.nextMatchId = nextMatchId;
        this.tournamentId = tournamentId;
        this.isNextMatchHome = isNextMatchHome;
        this.standing = standing;
    }

    public Match(long homePlayerId, long awayPlayerId, int homePlayerScore, int awayPlayerScore, long id, int nextMatchId,boolean isNextMatchHome, long tournamentId, int standing) {
        this.homePlayerId = homePlayerId;
        this.awayPlayerId = awayPlayerId;
        this.homePlayerScore = homePlayerScore;
        this.awayPlayerScore = awayPlayerScore;
        this.id = id;
        this.nextMatchId = nextMatchId;
        this.tournamentId = tournamentId;
        this.isNextMatchHome = isNextMatchHome;
        this.standing = standing;
    }

    public boolean isNextMatchHome() { return isNextMatchHome; }

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

    public long getId() {
        return id;
    }

    public long getNextMatchId() {
        return nextMatchId;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public Player getHomePlayer() {
        return homePlayer;
    }

    public void setHomePlayer(Player homePlayer) {
        this.homePlayer = homePlayer;
    }

    public Player getAwayPlayer() {
        return awayPlayer;
    }

    public int getStanding() {
        return standing;
    }

    public void setAwayPlayer(Player awayPlayer) {
        this.awayPlayer = awayPlayer;
    }

    public void setHomePlayerScore(int score) { this.homePlayerScore = score; }

    public void setAwayPlayerScore(int score) { this.awayPlayerScore = score; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (id != match.id) return false;
        return tournamentId == match.tournamentId;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (tournamentId ^ (tournamentId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Match{" +
                "homePlayerId=" + homePlayerId +
                ", awayPlayerId=" + awayPlayerId +
                ", homePlayerScore=" + homePlayerScore +
                ", awayPlayerScore=" + awayPlayerScore +
                ", id=" + id +
                ", tournamentId=" + tournamentId +
                ", nextMatchHome=" + isNextMatchHome +
                '}';
    }
}
