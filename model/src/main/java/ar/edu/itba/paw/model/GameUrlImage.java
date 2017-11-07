package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "game_url_image")
public class GameUrlImage {

    @Id
    @Column(name = "game_id")
    private Long gameId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "url_image")
    private String urlImage;

    public GameUrlImage() {
        /* for hibernate */
    }

    public GameUrlImage(Game game, String ulrImage) {
        this.game = game;
        this.urlImage = ulrImage;
    }

    public Game getGame() {
        return this.game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
