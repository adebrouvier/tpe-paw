package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserFollow;

public interface UserFollowService {

    public void create(User follower, User followed);

    public UserFollow findById(User follower, User followed);

    public void delete(User follower, User followed);

    public boolean isFollow(User follower, User followed);

}
