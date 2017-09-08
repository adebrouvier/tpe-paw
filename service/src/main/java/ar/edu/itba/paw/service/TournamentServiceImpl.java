package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TournamentServiceImpl implements TournamentService{

    @Autowired
    TournamentDao tournamentDao;

    @Override
    public Tournament findById(long id) {
        return tournamentDao.findById(id);
    }

    @Override
    public Tournament create(String name) {
        return tournamentDao.create(name);
    }
}
