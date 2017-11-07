package ar.edu.itba.paw.model;


import java.io.Serializable;

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
