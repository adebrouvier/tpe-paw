package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.persistence.GameDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Game;
import ar.edu.itba.paw.model.Standing;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class TournamentHibernateDao implements TournamentDao{

    @Autowired
    GameHibernateDao gameDao;

    @Autowired
    UserHibernateDao userDao;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Tournament findById(long id) {
        final TypedQuery<Tournament> query = em.createQuery("from Tournament as t where t.id = :id", Tournament.class);
        query.setParameter("id", id);
        final List<Tournament> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Transactional
    @Override
    public Tournament create(String name, long gameId, long userId) {
        final Game game = gameDao.findById(gameId);
        final User user = userDao.findById(userId);
        final Tournament tournament = new Tournament(name, game, Tournament.Status.NEW, user);
        em.persist(tournament);
        return tournament;
}

    @Override
    public List<Tournament> findFeaturedTournaments(int featured) {
        return null;
    }

    @Override
    public void setStatus(long tournamentId, Tournament.Status status) {

    }

    @Override
    public List<Standing> getStandings(long tournamentId) {
        return null;
    }

    @Override
    public List<String> findTournamentNames(String query) {
        return null;
    }

    @Override
    public List<String> findTournamentNames(String query, long gameId) {
        return null;
    }

    @Override
    public List<Tournament> findByName(String name) {
        return null;
    }

    @Override
    public Tournament getByName(String name) {
        return null;
    }

    @Override
    public boolean participatesIn(long userId, long tournamentId) {
        return false;
    }

    @Override
    public Tournament getByNameAndGameId(String tournamentName, long gameId) {
        return null;
    }
}
