package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.InscriptionDao;
import ar.edu.itba.paw.model.Inscription;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class InscriptionHibernateDao implements InscriptionDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Inscription create(User user, Tournament tournament) {

        if (user == null || tournament == null) {
            return null;
        }

        Inscription i = new Inscription(user, tournament);

        em.merge(i);

        return i;
    }

    @Override
    public Inscription findByIds(long loggedUserId, long tournamentId) {

        TypedQuery<Inscription> query = em.createQuery("from Inscription " +
                        "where tournament.id = :tournamentId and user.id = :loggedUserId",
                Inscription.class)
                .setParameter("loggedUserId", loggedUserId)
                .setParameter("tournamentId", tournamentId);

        List<Inscription> res = query.getResultList();

        return res.isEmpty() ? null : res.get(0);
    }

    @Override
    public List<Inscription> findByTournamentId(long tournamentId) {

        TypedQuery<Inscription> query = em.createQuery("from Inscription " +
                "where tournament.id = :tournamentId", Inscription.class)
                .setParameter("tournamentId", tournamentId);

        return query.getResultList();
    }

    @Override
    public void delete(long tournamentId, long userId) {

        Inscription i = findByIds(userId, tournamentId);

        if (i == null) {
            return;
        }

        if (em.contains(i)) {
            em.remove(i);
        }

    }
}
