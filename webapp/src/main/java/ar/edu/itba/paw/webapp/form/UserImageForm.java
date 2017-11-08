package ar.edu.itba.paw.webapp.form;

import org.springframework.web.multipart.MultipartFile;

public class UserImageForm {

    private MultipartFile image;

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public MultipartFile getImage(){
        return image;
    }
}
