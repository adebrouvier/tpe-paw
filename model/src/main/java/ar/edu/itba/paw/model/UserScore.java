package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "ranking_players")
@IdClass(UserScorePK.class)
public class UserScore {

    @Column(name = "points")
    private int points;

    @Id
    @ManyToOne
    @JoinColumn(name = "ranking_id")
    private Ranking ranking;

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id")
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

}
