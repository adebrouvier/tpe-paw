package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.GameImageDao;
import ar.edu.itba.paw.interfaces.service.GameImageService;
import ar.edu.itba.paw.model.GameImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameImageServiceImpl implements GameImageService{

    @Autowired
    private GameImageDao gameImageDao;


    @Override
    public GameImage findById(long gameId) {
        return gameImageDao.findById(gameId);
    }
}
