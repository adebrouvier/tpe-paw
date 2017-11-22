package ar.edu.itba.paw.model;

import java.io.Serializable;

public class MatchPK implements Serializable {

    protected Integer id;
    protected Tournament tournament;

    public MatchPK() {
    }

    public MatchPK(Integer id, Tournament tournament) {
        this.id = id;
        this.tournament = tournament;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchPK matchPK = (MatchPK) o;

        if (!id.equals(matchPK.id)) return false;
        return tournament.equals(matchPK.tournament);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + tournament.hashCode();
        return result;
    }
}
