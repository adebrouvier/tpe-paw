package ar.edu.itba.paw.model;


import java.io.Serializable;

public class UserScorePK implements Serializable {

    protected User user;
    protected Ranking ranking;

    public UserScorePK() {}

    public UserScorePK(Ranking ranking, User user) {
        this.user = user;
        this.ranking = ranking;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserScorePK that = (UserScorePK) o;

        if (!user.equals(that.user)) return false;
        return ranking.equals(that.ranking);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + ranking.hashCode();
        return result;
    }
}
