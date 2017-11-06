package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "game_url_image")
public class GameUrlImage {

    @Id
    @Column(name = "game_id")
    private long id;

    @Column(name = "url_image")
    private String image;

    public GameUrlImage() {
        /* for hibernate */
    }

    public GameUrlImage(long id, String image) {
        this.id = id;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUrlImage() {
        return image;
    }

    public void setUrlImage(String image) {
        this.image = image;
    }
}
