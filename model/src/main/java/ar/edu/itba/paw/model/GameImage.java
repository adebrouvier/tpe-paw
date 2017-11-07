package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "game_image")
public class GameImage {

    @Id
    @Column(name = "game_id")
    private Long gameId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "image")
    private byte[] image;

    public GameImage() {
        /* for hibernate */
    }

    public GameImage(Game game,byte[] image) {
        this.game = game;
        this.image = image;
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

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
