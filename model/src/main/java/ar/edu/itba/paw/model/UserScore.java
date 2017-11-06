package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "ranking_players")
@SecondaryTable(name="users", pkJoinColumns={
        @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="user_id")})
public class UserScore {

    @Column(name = "points")
    private int points;

    @Id
    @Column(name = "ranking_id")
    private long rankingId;

    @Id
    @Column(name = "user_id")
    private long userId;

    @Column(table = "users", name = "user_name")
    private long name;

    public UserScore(){
        /* for hibernate */
    }

    public UserScore(long rankingId, long userId, int points){
        this.rankingId = rankingId;
        this.userId = userId;
        this.points = points;
    }

    public long getRankingId() {
        return rankingId;
    }

    public void setRankingId(long rankingId) {
        this.rankingId = rankingId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
