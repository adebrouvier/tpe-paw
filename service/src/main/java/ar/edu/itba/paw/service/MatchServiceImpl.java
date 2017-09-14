package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService{

    @Autowired
    MatchDao matchDao;

    @Override
    public Match create(Integer matchId, Integer nextMatchId, long tournamentId) {
        return matchDao.create(matchId,nextMatchId,tournamentId);
    }

    @Override
    public Match create(Integer matchId, Integer nextMatchId, long tournamentId, long localPlayerId, long visitorPlayerId) {
        return matchDao.create(matchId,nextMatchId,tournamentId,localPlayerId,visitorPlayerId);
    }

    @Override
    public Match findById(Integer id, long tournamentId) {
        return matchDao.findById(id,tournamentId);
    }

    @Override
    public Match addPlayer(long tournamentId, int matchId, long playerId, Integer type) {
        return matchDao.addPlayer(tournamentId,matchId,playerId,type);
    }

    @Override
    public Match updateScore(long tournamentId, int matchId, Integer localScore, Integer visitorScore) {
        return matchDao.updateScore(tournamentId,matchId,localScore,visitorScore);
    }
}
