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
    public Match create(int matchId, int nextMatchId,boolean isNextMatchHome, long tournamentId) {
        return matchDao.create(matchId,nextMatchId,isNextMatchHome,tournamentId);
    }

    @Override
    public Match create(int matchId, int nextMatchId,boolean isNextMatchHome, long tournamentId, long homePlayerId, long awayPlayerId) {
        return matchDao.create(matchId,nextMatchId,isNextMatchHome,tournamentId,homePlayerId,awayPlayerId);
    }

    public Match createWinnerMatch(int matchId, int nextMatchId, int loserMatchId, boolean isNextMatchHome, long tournamentId){
        return matchDao.createWinnerMatch(matchId,nextMatchId,loserMatchId,isNextMatchHome,tournamentId);
    }

    @Override
    public Match createWinnerMatch(int matchId, int nextMatchId, int loserMatchId, boolean isNextMatchHome, long tournamentId, long homePlayerId, long awayPlayerId) {
        return matchDao.createWinnerMatch(matchId,nextMatchId,loserMatchId,isNextMatchHome,tournamentId,homePlayerId,awayPlayerId);

    }

    @Override
    public Match createLoserMatch(int matchId, int nextMatchId, boolean isNextMatchHome, long tournamentId) {
        return matchDao.createLoserMatch(matchId,nextMatchId,isNextMatchHome,tournamentId);
    }

    @Override
    public Match findById(int id, long tournamentId) {
        return matchDao.findById(id,tournamentId);
    }

    @Override
    public Match addPlayer(long tournamentId, int matchId, long playerId, int type) {
        return matchDao.addPlayer(tournamentId,matchId,playerId,type);
    }

    @Override
    public Match updateScore(long tournamentId, int matchId,int localScore, int visitorScore) {
        return matchDao.updateScore(tournamentId,matchId,localScore,visitorScore);
    }
}
