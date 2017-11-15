package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "match")
@IdClass(MatchPK.class)
public class Match {

    @Id
    @Column(name ="match_id")
    private Integer id;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "home_player_id")
    private Player homePlayer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "away_player_id")
    private Player awayPlayer;

    @Column(name = "home_player_score")
    private Integer homePlayerScore;

    @Column(name = "away_player_score")
    private Integer awayPlayerScore;

    @OneToOne(fetch = FetchType.EAGER)
    private Match nextMatch;

    @Column(name = "next_match_home")
    private Boolean nextMatchHome;

    @Column(name = "vod_link")
    private String videoOnDemandLink;

    private int standing;

    Match(){
        /* For Hibernate */
    }

    public Match (Integer id, Match nextMatch, boolean nextMatchHome, Tournament tournament, int standing) {
        this.id = id;
        this.nextMatch = nextMatch;
        this.tournament = tournament;
        this.nextMatchHome = nextMatchHome;
        this.standing = standing;
    }

    public Match (Integer id, Player homePlayer, Player awayPlayer, Match nextMatch, boolean nextMatchHome, Tournament tournament, int standing) {
        this.id = id;
        this.homePlayer = homePlayer;
        this.awayPlayer = awayPlayer;
        this.nextMatch = nextMatch;
        this.tournament = tournament;
        this.nextMatchHome = nextMatchHome;
        this.standing = standing;
    }

    public Match(Integer id, Player homePlayer, Player awayPlayer, int homePlayerScore, int awayPlayerScore, Match nextMatch, boolean nextMatchHome, Tournament tournament, int standing) {
        this.id = id;
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

    public Integer getHomePlayerScore() {
        return homePlayerScore;
    }

    public Integer getAwayPlayerScore() {
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

    public void setLinkToVideoOnDemand(String link) {   this.videoOnDemandLink = link;  }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Match match = (Match) o;

        if (!id.equals(match.id)) return false;
        return tournament.equals(match.tournament);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + tournament.hashCode();
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
