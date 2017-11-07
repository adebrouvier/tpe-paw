package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ranking_tournaments")
@IdClass(TournamentPoints.TournamentPointsPK.class)
public class TournamentPoints {

    @Column(name = "awarded_points")
    private int awardedPoints;

    @Id
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

    public class TournamentPointsPK implements Serializable {

        protected Tournament tournament;
        protected Ranking ranking;

        public TournamentPointsPK() {}

        public TournamentPointsPK(Ranking ranking, Tournament tournament) {
            this.ranking = ranking;
            this.tournament = tournament;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TournamentPointsPK that = (TournamentPointsPK) o;

            if (!tournament.equals(that.tournament)) return false;
            return ranking.equals(that.ranking);
        }

        @Override
        public int hashCode() {
            int result = tournament.hashCode();
            result = 31 * result + ranking.hashCode();
            return result;
        }
    }
}
