package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.RankingDao;
import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RankingServiceImpl implements RankingService {

    @Autowired
    private RankingDao rankingDao;

    @Override
    public Ranking findById(long rankingId) {
        return rankingDao.findById(rankingId);
    }

    @Override
    public Ranking create(String name, Map<Tournament, Integer> tournaments) {
        return rankingDao.create(name,tournaments);
    }

    @Override
    public List<Ranking> findByName(String term) {
        return rankingDao.findByName(term);
    }

    @Override
    public List<String> findRankingNames(String query) {
        return rankingDao.findRankingNames(query);
    }

    @Override
    public void delete(long rankingId, long tournamentId) {
        rankingDao.delete(rankingId, tournamentId);
    }


}
