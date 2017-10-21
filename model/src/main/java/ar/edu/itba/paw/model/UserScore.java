package ar.edu.itba.paw.model;

public class UserScore {
    /*This could be only the id*/
    private long userId;
    private int points;
    private String userName;

    public UserScore(long userId, int points){
        this.userId = userId;
        this.points = points;
    }

    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
