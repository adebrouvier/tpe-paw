package ar.edu.itba.paw.model;

import javax.persistence.*;

@Table(name = "ranking_tournaments")
public class TournamentPoints {

    @Column(name = "awarded_points")
    private int awardedPoints;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Ranking ranking;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Tournament tournament;

    TournamentPoints() {
        /* for Hibernate */
    }

    public TournamentPoints(Ranking ranking, Tournament tournament, int awardedPoints){
        this.ranking = ranking;
        this.tournament = tournament;
        this.awardedPoints = awardedPoints;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournamentId) {
        this.tournament = tournament;
    }

    public int getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(int awardedPoints) {
        this.awardedPoints = awardedPoints;
    }
}
