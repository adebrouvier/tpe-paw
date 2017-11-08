package ar.edu.itba.paw.webapp.form;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Size;
import java.util.List;

public class UserUpdateForm {

    private MultipartFile image;

    @Size(min = 0, max = 200)
    private String description;

    @Size(min = 0, max = 60)
    private String game;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile getImage(){
        return image;
    }
}
