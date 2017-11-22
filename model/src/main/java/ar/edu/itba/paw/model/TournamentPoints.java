package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "ranking_tournaments")
@IdClass(TournamentPointsPK.class)
public class TournamentPoints {

    @Column(name = "awarded_points")
    private int awardedPoints;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ranking_id")
    private Ranking ranking;

    @Id
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "tournament_id")
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

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public int getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(int awardedPoints) {
        this.awardedPoints = awardedPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TournamentPoints that = (TournamentPoints) o;

        if (ranking != null ? !ranking.equals(that.ranking) : that.ranking != null) return false;
        return tournament != null ? tournament.equals(that.tournament) : that.tournament == null;
    }

    @Override
    public int hashCode() {
        int result = ranking != null ? ranking.hashCode() : 0;
        result = 31 * result + (tournament != null ? tournament.hashCode() : 0);
        return result;
    }
}
