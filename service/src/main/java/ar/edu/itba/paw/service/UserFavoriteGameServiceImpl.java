package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.UserFavoriteGameDao;
import ar.edu.itba.paw.interfaces.service.UserFavoriteGameService;
import ar.edu.itba.paw.model.Game;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserFavoriteGame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFavoriteGameServiceImpl implements UserFavoriteGameService {

    @Autowired
    private UserFavoriteGameDao userFavoriteGameDao;

    @Override
    public void create(User user, Game game) {
        userFavoriteGameDao.create(user, game);
    }

    @Override
    public void delete(User user, Game game) {
        userFavoriteGameDao.delete(user, game);
    }

    @Override
    public void deleteAll(User user) {
        userFavoriteGameDao.deleteAll(user);
    }

    @Override
    public List<UserFavoriteGame> getFavoriteGames(User user) {
        return userFavoriteGameDao.getFavoriteGames(user);
    }
}
