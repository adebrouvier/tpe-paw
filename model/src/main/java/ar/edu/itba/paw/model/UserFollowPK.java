package ar.edu.itba.paw.model;


import java.io.Serializable;

public class UserFollowPK implements Serializable {

    protected Long userFollowerId;
    protected Long userFollowedId;

    public UserFollowPK() {}

    public UserFollowPK(Long userFollowerId, Long userFollowedId) {
        this.userFollowerId = userFollowerId;
        this.userFollowedId = userFollowedId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFollowPK)) return false;

        UserFollowPK that = (UserFollowPK) o;

        if (!userFollowerId.equals(that.userFollowerId)) return false;
        return userFollowedId.equals(that.userFollowedId);
    }

    @Override
    public int hashCode() {
        int result = userFollowerId.hashCode();
        result = 31 * result + userFollowedId.hashCode();
        return result;
    }
}

