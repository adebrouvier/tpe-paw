package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.RankingDao;
import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Ranking create(String name, Map<Tournament, Integer> tournaments, String game, long userId) {
        return rankingDao.create(name,tournaments, game, userId);
    }

    @Override
    public List<Ranking> findByName(String term, String game) {
        return rankingDao.findByName(term, game);
    }

    @Override
    public List<String> findRankingNames(String query) {
        return rankingDao.findRankingNames(query);
    }

    @Transactional
    @Override
    public Ranking addTournaments(long rankingId, Map<Tournament, Integer> tournaments) {
        return rankingDao.addTournaments(rankingId,tournaments);
    }

    @Override
    public List<Ranking> findFeaturedRankings(int featured) {
        return rankingDao.findFeaturedRankings(featured);
    }

    @Override
    public List<Ranking> findRankingByUser(long userId) {
        return rankingDao.findRankingByUser(userId);
    }

    @Transactional
    @Override
    public void delete(long rankingId, long tournamentId) {
        rankingDao.delete(rankingId, tournamentId);
    }


}
