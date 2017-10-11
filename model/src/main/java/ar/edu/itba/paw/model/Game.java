package ar.edu.itba.paw.model;


public class Game {

    private long gameId;
    private String name;

    public Game(long gameId, String name) {
        this.gameId = gameId;
        this.name = name;
    }

    public long getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }
}
