package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ranking_players")
@IdClass(UserScore.UserScorePK.class)
public class UserScore {

    @Column(name = "points")
    private int points;

    @Id
    @ManyToOne
    private Ranking ranking;

    @Id
    @ManyToOne
    private User user;

    UserScore(){
        /* for hibernate */
    }

    public UserScore(Ranking ranking, User user, int points){
        this.ranking = ranking;
        this.user = user;
        this.points = points;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

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

}
