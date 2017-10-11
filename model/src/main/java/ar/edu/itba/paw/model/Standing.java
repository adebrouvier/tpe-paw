package ar.edu.itba.paw.model;

public class Standing {

    private String playerName;
    private int position;

    public Standing(String playerName, int position) {
        this.playerName = playerName;
        this.position = position;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPosition() {
        return position;
    }

}
