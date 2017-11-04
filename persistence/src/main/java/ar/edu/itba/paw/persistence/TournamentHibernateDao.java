package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.interfaces.persistence.GameDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.interfaces.persistence.UserDao;
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
    private GameDao gameDao;

    @Autowired
    private UserDao userDao;

    @PersistenceContext
    private EntityManager em;

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
    public Tournament findById(long id) {
        return em.find(Tournament.class, id);
    }

    @Override
    public List<Tournament> findFeaturedTournaments(int featured) {
        final TypedQuery<Tournament> query = em.createQuery("from Tournament as t ORDER BY t.id DESC LIMIT :featured", Tournament.class);
        query.setParameter("featured", featured);
        final List<Tournament> list = query.getResultList();
        return list.isEmpty() ? null : list;
    }

    @Override
    public List<Standing> getStandings(long tournamentId) {
        //Not necessary anymore
        return null;
    }

    @Override
    public List<String> findTournamentNames(String name) {
        StringBuilder sb = new StringBuilder(name.toLowerCase());
        sb.insert(0,"%");
        sb.append("%");

        TypedQuery<String> query = em.createQuery("from Tournament as t WHERE lower(t.name) LIKE :name", String.class);
        query.setParameter("name", sb.toString());

        return query.getResultList();
    }

    @Override
    public List<String> findTournamentNames(String term, long gameId) {
        StringBuilder sb = new StringBuilder(term.toLowerCase());
        sb.insert(0,"%");
        sb.append("%");

        TypedQuery<String> query = em.createQuery("from Tournament as t " +
                "WHERE lower(t.name) LIKE :name AND game.id = :gameId", String.class);
        query.setParameter("name", sb.toString());
        query.setParameter("game", gameId);

        return query.getResultList();
    }

    @Override
    public List<Tournament> findByName(String name) {

        StringBuilder sb = new StringBuilder(name.toLowerCase());
        sb.insert(0,"%");
        sb.append("%");

        TypedQuery<Tournament> query = em.createQuery("from Tournament as t WHERE lower(t.name) LIKE :name", Tournament.class);
        query.setParameter("name", sb.toString());
        List<Tournament> list = query.getResultList();

        return list.isEmpty() ? null : list;
    }

    @Override
    public Tournament getByName(String name) {
        TypedQuery<Tournament> query = em.createQuery("from Tournament as t WHERE t.name = :name", Tournament.class);
        query.setParameter("name", name);
        List<Tournament> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public Tournament setStatus(long tournamentId, Tournament.Status status) {
        final Tournament t = findById(tournamentId);

        if (t != null) {
            t.setStatus(status);
        }

        return t;
    }

    @Override
    public boolean participatesIn(long userId, long tournamentId) {
        TypedQuery<Integer> query = em.createQuery("SELECT count(*) FROM Player WHERE " +
                "tournament.id = :tournamentId and user.id = :userId", Integer.class);
        List<Integer> list = query.getResultList();
        return !list.isEmpty();
    }

    @Override
    public Tournament getByNameAndGameId(String name, long gameId) {
        TypedQuery<Tournament> query = em.createQuery("from Tournament as t WHERE t.name = :name" +
                "AND t.game.id = :gameId AND t.status = :status", Tournament.class);
        query.setParameter("name", name);
        query.setParameter("gameId", gameId);
        query.setParameter("status", Tournament.Status.FINISHED);
        List<Tournament> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }
}
