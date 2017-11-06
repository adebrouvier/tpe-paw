package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.GameUrlImageDao;
import ar.edu.itba.paw.model.GameUrlImage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GameUrlImageHibernateDao implements GameUrlImageDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(long gameId, String url) {
        GameUrlImage gui = new GameUrlImage(gameId, url);
        em.persist(gui);
    }

    @Override
    public String findById(long gameId) {
        return em.find(GameUrlImage.class, gameId).getUrlImage();
    }
}
