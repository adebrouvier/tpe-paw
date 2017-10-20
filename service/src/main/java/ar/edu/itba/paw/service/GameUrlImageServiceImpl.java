package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.GameUrlImageDao;
import ar.edu.itba.paw.interfaces.service.GameUrlImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameUrlImageServiceImpl implements GameUrlImageService {

    @Autowired
    private GameUrlImageDao gameUrlImageDao;

    @Override
    public String findById(long gameId) {
        return gameUrlImageDao.findById(gameId);
    }
}
