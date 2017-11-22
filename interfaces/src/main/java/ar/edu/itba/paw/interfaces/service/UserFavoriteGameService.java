package ar.edu.itba.paw.interfaces.service;


import ar.edu.itba.paw.model.Game;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserFavoriteGame;

import java.util.List;

public interface UserFavoriteGameService {

    public void create(User user, Game game);

    public void delete(User user, Game game);

    public void deleteAll(User user);

    public List<UserFavoriteGame> getFavoriteGames(User user);
}
