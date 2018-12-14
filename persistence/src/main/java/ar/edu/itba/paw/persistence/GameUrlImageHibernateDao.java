package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.GameUrlImageDao;
import ar.edu.itba.paw.model.Game;
import ar.edu.itba.paw.model.GameUrlImage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class GameUrlImageHibernateDao implements GameUrlImageDao {

  @PersistenceContext
  private EntityManager em;

  @Override
  public void create(Game game, String url) {
    GameUrlImage gui = new GameUrlImage(game.getId(), url);
    em.persist(gui);
  }

  @Override
  public String findById(long gameId) {
    return em.find(GameUrlImage.class, gameId).getUrlImage();
  }
}
