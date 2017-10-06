package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.GameDao;
import ar.edu.itba.paw.interfaces.service.GameService;
import ar.edu.itba.paw.model.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService{

    @Autowired
    private GameDao gameDao;

    @Override
    public List<Game> getGames() {
        return gameDao.getGames();
    }
}
