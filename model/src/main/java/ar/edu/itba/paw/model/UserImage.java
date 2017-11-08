package ar.edu.itba.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_image")
public class UserImage {

    @Id
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "image")
    private byte[] image;

    public UserImage() {
        /* for Hibernate */
    }

    public UserImage(Long user_id, byte[] image) {
        this.user_id = user_id;
        this.image = image;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
