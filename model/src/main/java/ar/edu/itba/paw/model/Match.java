package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "match")
public class Match {

    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player homePlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    private Player awayPlayer;

    @Column(name = "home_player_score")
    private int homePlayerScore;

    @Column(name = "away_player_score")
    private int awayPlayerScore;

    private Match nextMatch;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Tournament tournament;

    @Column(name = "next_match_home")
    private Boolean nextMatchHome;
    private int standing;

    Match(){
        /* For Hibernate */
    }

    public Match (Match nextMatch, boolean nextMatchHome, Tournament tournament, int standing) {
        this.nextMatch = nextMatch;
        this.tournament = tournament;
        this.nextMatchHome = nextMatchHome;
        this.standing = standing;
    }

    public Match (Player homePlayer, Player awayPlayer, Match nextMatch, boolean nextMatchHome, Tournament tournament, int standing) {
        this.homePlayer = homePlayer;
        this.awayPlayer = awayPlayer;
        this.nextMatch = nextMatch;
        this.tournament = tournament;
        this.nextMatchHome = nextMatchHome;
        this.standing = standing;
    }

    public Match(Player homePlayer, Player awayPlayer, int homePlayerScore, int awayPlayerScore, Match nextMatch, boolean nextMatchHome, Tournament tournament, int standing) {
        this.homePlayer = homePlayer;
        this.awayPlayer = awayPlayer;
        this.homePlayerScore = homePlayerScore;
        this.awayPlayerScore = awayPlayerScore;
        this.nextMatch = nextMatch;
        this.tournament = tournament;
        this.nextMatchHome = nextMatchHome;
        this.standing = standing;
    }

    public boolean isNextMatchHome() { return nextMatchHome; }

    public int getHomePlayerScore() {
        return homePlayerScore;
    }

    public int getAwayPlayerScore() {
        return awayPlayerScore;
    }

    public long getId() {
        return id;
    }

    public Match getNextMatch() {
        return nextMatch;
    }

    public Tournament getTournament() {
        return tournament;
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
        return tournament.getId() == match.getTournament().getId();
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (tournament.getId() ^ (tournament.getId() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Match{" +
                "homePlayer=" + homePlayer +
                ", awayPlayer=" + awayPlayer +
                ", homePlayerScore=" + homePlayerScore +
                ", awayPlayerScore=" + awayPlayerScore +
                ", id=" + id +
                ", tournament=" + tournament +
                ", nextMatchHome=" + nextMatchHome +
                '}';
    }
}
