package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.model.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatchServiceImpl implements MatchService {

  @Autowired
  private MatchDao matchDao;

  @Transactional
  @Override
  public Match createEmpty(int matchId, int nextMatchId, boolean isNextHome, long tournamentId, int standing) {
    return matchDao.createEmpty(matchId, nextMatchId, isNextHome, tournamentId, standing);
  }

  @Transactional
  @Override
  public Match create(int matchId, int nextMatchId, boolean isNextMatchHome, long tournamentId, long localPlayerId, long visitorPlayerId, int standing) {
    return matchDao.create(matchId, nextMatchId, isNextMatchHome, tournamentId, localPlayerId, visitorPlayerId, standing);
  }

  @Override
  public Match findById(int id, long tournamentId) {
    return matchDao.findById(id, tournamentId);
  }

  @Transactional
  @Override
  public Match updateScore(long tournamentId, int matchId, int localScore, int visitorScore) {
    return matchDao.updateScore(tournamentId, matchId, localScore, visitorScore);
  }

  @Transactional
  @Override
  public void setVODLink(String link, long tournamentId, int matchId) {
    matchDao.addVideoOnDemand(link, tournamentId, matchId);
  }

  @Transactional
  @Override
  public void setMap(String mapName, long tournamentId, int matchId) {
    matchDao.addMap(mapName, tournamentId, matchId);
  }

  @Transactional
  @Override
  public void setHomePlayerCharacter(String characterName, long tournamentId, int matchId) {
    matchDao.addHomePlayerCharacter(characterName, tournamentId, matchId);
  }

  @Transactional
  @Override
  public void setAwayPlayerCharacter(String characterName, long tournamentId, int matchId) {
    matchDao.addAwayPlayerCharacter(characterName, tournamentId, matchId);
  }


}
