package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.GameDao;
import ar.edu.itba.paw.model.Game;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class GameHibernateDao implements GameDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Game create(String name, boolean userGenerated) {
        final Game game = new Game(name, userGenerated);
        em.persist(game);
        return game;
    }

    @Override
    public Game findById(long id) {
        return em.find(Game.class, id);
    }

    @Override
    public Game findByName(String name) {
        final TypedQuery<Game> query = em.createQuery("from Game as g where g.name = :name", Game.class);
        query.setParameter("name", name);
        final List<Game> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<String> findGameNames(String term) {
        final TypedQuery<String> query = em.createQuery("select g.name from Game as g where g.name LIKE :term", String.class);
        query.setParameter("term", term.concat("%"));
        return query.getResultList();
    }
}
