package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserImage;

public interface UserImageService {

    public void updateImage(User user, byte[] image);

    public UserImage findById(long id);
}
