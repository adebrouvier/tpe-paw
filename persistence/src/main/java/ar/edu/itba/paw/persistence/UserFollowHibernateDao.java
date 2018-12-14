package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserFollowDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserFollow;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserFollowHibernateDao implements UserFollowDao {

  @PersistenceContext
  private EntityManager em;

  @Transactional
  @Override
  public void create(User follower, User followed) {
    if (follower == null || followed == null) {
      return;
    }

    UserFollow u = findById(follower, followed);
    if (u == null) {
      u = new UserFollow(follower.getId(), followed.getId());
      em.persist(u);
    }
  }

  @Override
  public UserFollow findById(User follower, User followed) {
    if (follower == null || followed == null) {
      return null;
    }
    List<UserFollow> list = em.createQuery("FROM UserFollow AS u WHERE u.userFollower.id = :userFollowerId AND u.userFollowed.id = :userFollowedId", UserFollow.class)
      .setParameter("userFollowerId", follower.getId())
      .setParameter("userFollowedId", followed.getId())
      .getResultList();
    if (list == null || list.isEmpty()) {
      return null;
    }

    return list.get(0);
  }

  @Transactional
  @Override
  public void delete(User follower, User followed) {
    if (follower == null || followed == null) {
      return;
    }

    UserFollow u = findById(follower, followed);
    if (u != null) {
      em.remove(u);
    }
  }

  @Override
  public boolean isFollow(User follower, User followed) {
    return findById(follower, followed) != null;
  }

  @Override
  public List<UserFollow> getFollowers(User user) {
    if (user == null) {
      return null;
    }
    List<UserFollow> list = em.createQuery("FROM UserFollow AS u WHERE u.userFollowedId = :userId", UserFollow.class)
      .setParameter("userId", user.getId())
      .getResultList();
    if (list == null || list.isEmpty()) {
      return null;
    }
    return list;
  }
}
