package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements MatchService{

    @Autowired
    private MatchDao matchDao;

    @Override
    public Match createEmpty(int matchId, int nextMatchId, boolean isNextHome, long tournamentId) {
        return matchDao.createEmpty(matchId,nextMatchId,isNextHome,tournamentId);
    }

    @Override
    public Match create(int matchId, int nextMatchId,boolean isNextMatchHome, long tournamentId, long localPlayerId, long visitorPlayerId) {
        return matchDao.create(matchId,nextMatchId,isNextMatchHome,tournamentId,localPlayerId,visitorPlayerId);
    }

    @Override
    public Match findById(int id, long tournamentId) {
        return matchDao.findById(id,tournamentId);
    }

    @Override
    public Match updateScore(long tournamentId, int matchId,int localScore, int visitorScore) {
        return matchDao.updateScore(tournamentId,matchId,localScore,visitorScore);
    }
}
