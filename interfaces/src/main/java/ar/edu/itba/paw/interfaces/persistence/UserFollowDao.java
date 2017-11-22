package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserFollow;

import java.util.List;

public interface UserFollowDao {

    public void create(User follower, User followed);

    public UserFollow findById(User follower, User followed);

    public void delete(User follower, User followed);

    public boolean isFollow(User follower, User followed);

    public List<UserFollow> getFollowers(User user);

}
