package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.GameImageDao;
import ar.edu.itba.paw.model.GameImage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GameImageHibernateDao implements GameImageDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public GameImage findById(long gameId) {
        return em.find(GameImage.class, gameId);
    }
}
