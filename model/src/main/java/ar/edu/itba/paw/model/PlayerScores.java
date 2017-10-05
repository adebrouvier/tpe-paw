package ar.edu.itba.paw.model;

public class PlayerScores {
    /*This could be only the id*/
    private int playerId;
    private int points;

    public PlayerScores(int playerId, int points){
        this.playerId = playerId;
        this.points = points;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
