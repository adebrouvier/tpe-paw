package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserImage;

public interface UserImageDao {

  public void updateImage(User user, byte[] image);

  public UserImage findById(long id);
}
