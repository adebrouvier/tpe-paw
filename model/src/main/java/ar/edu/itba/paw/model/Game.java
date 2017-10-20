package ar.edu.itba.paw.model;


public class Game {

    private long gameId;
    private String name;
    private String urlImage;

    public Game(long gameId, String name, String urlImage) {
        this.gameId = gameId;
        this.name = name;
        this.urlImage = urlImage;
    }

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

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
