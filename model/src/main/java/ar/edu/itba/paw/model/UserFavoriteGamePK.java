package ar.edu.itba.paw.model;

import java.io.Serializable;

public class UserFavoriteGamePK implements Serializable {

    protected Long userId;
    protected Long gameId;

    public UserFavoriteGamePK() {
    }

    public UserFavoriteGamePK(Long userId, Long gameId) {
        this.userId = userId;
        this.gameId = gameId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFavoriteGamePK)) return false;

        UserFavoriteGamePK that = (UserFavoriteGamePK) o;

        if (!userId.equals(that.userId)) return false;
        return gameId.equals(that.gameId);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + gameId.hashCode();
        return result;
    }
}
