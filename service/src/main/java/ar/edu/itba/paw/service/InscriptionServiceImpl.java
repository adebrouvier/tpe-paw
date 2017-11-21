package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.InscriptionDao;
import ar.edu.itba.paw.interfaces.service.InscriptionService;
import ar.edu.itba.paw.model.Inscription;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class InscriptionServiceImpl implements InscriptionService{

    @Autowired
    private InscriptionDao inscriptionDao;

    @Override
    @Transactional
    public Inscription create(User user, Tournament tournament) {
        return inscriptionDao.create(user, tournament);
    }

    @Override
    public Inscription findByIds(long loggedUserId, long tournamentId) {
        return inscriptionDao.findByIds(loggedUserId, tournamentId);
    }

    @Override
    public List<Inscription> finByTournamentId(long tournamentId) {
        return inscriptionDao.findByTournamentId(tournamentId);
    }

    @Override
    @Transactional
    public void delete(long tournamentId, long userId) {
        inscriptionDao.delete(tournamentId, userId);
    }
}
