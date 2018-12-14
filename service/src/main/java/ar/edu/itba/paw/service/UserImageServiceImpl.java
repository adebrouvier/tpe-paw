package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.UserImageDao;
import ar.edu.itba.paw.interfaces.service.UserImageService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserImageServiceImpl implements UserImageService {

  @Autowired
  private UserImageDao userImageDao;

  @Override
  public void updateImage(User user, byte[] image) {
    userImageDao.updateImage(user, image);
  }

  @Override
  public UserImage findById(long id) {
    return userImageDao.findById(id);
  }
}
