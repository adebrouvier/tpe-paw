package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "game_image")
public class GameImage {

    @Id
    @Column(name = "game_id")
    private long id;

    @Column(name = "image")
    private byte[] image;

    public GameImage() {
        /* for hibernate */
    }

    public GameImage(long id, byte[] image) {
        this.id = id;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
