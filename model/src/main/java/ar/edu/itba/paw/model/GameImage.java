package ar.edu.itba.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game_image")
public class GameImage {

    @Id
    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "image")
    private byte[] image;

    public GameImage() {
        /* for hibernate */
    }

    public GameImage(Long gameId,byte[] image) {
        this.gameId = gameId;
        this.image = image;
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
