package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.UserFollowDao;
import ar.edu.itba.paw.interfaces.service.UserFollowService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserFollowServiceImpl implements UserFollowService {

  @Autowired
  private UserFollowDao userFollowDao;

  @Override
  public void create(User follower, User followed) {
    userFollowDao.create(follower, followed);
  }

  @Override
  public UserFollow findById(User follower, User followed) {
    return userFollowDao.findById(follower, followed);
  }

  @Override
  public void delete(User follower, User followed) {
    userFollowDao.delete(follower, followed);
  }

  @Override
  public boolean isFollow(User follower, User followed) {
    return userFollowDao.isFollow(follower, followed);
  }
}
