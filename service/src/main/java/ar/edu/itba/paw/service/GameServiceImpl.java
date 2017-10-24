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
    public List<String> findGameNames(String query) {
        return gameDao.findGameNames(query);
    }

    @Override
    public Game findById(long id) {
        return gameDao.findById(id);
    }

    @Override
    public Game findByName(String name) {
        return gameDao.findByName(name);
    }

    @Override
    public Game create(String name, boolean userGenerated) {
        return gameDao.create(name, userGenerated);
    }
}
