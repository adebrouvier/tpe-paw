package ar.edu.itba.paw.model;

public class PlayerScores {
    /*This could be only the id*/
    private String name;
    private int points;

    public PlayerScores(String name, int points){
        this.name = name;
        this.points = points;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
}
