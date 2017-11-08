package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserFavoriteGameDao;
import ar.edu.itba.paw.model.Game;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserFavoriteGame;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserFavoriteGameHibernateDao implements UserFavoriteGameDao {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void create(User user, Game game) {
        if(user == null || game == null) {
            return;
        }

        UserFavoriteGame u = findById(user, game);
        if(u == null) {
            u = new UserFavoriteGame(user.getId(), game.getId());
            em.persist(u);
        }
    }

    @Transactional
    @Override
    public void delete(User user, Game game) {
        if(user == null || game == null) {
            return;
        }

        UserFavoriteGame u = findById(user, game);
        if(u != null) {
            em.remove(u);
        }
    }

    @Transactional
    @Override
    public void deleteAll(User user) {
        if(user == null) {
            return;
        }
        em.createQuery("DELETE FROM UserFavoriteGame AS u WHERE u.user.id = :userId")
                .setParameter("userId", user.getId())
                .executeUpdate();
    }

    @Override
    public List<UserFavoriteGame> getFavoriteGames(User user) {
        if(user == null) {
            return null;
        }

        List<UserFavoriteGame> list = em.createQuery("FROM UserFavoriteGame AS u WHERE u.user.id = :userId", UserFavoriteGame.class)
                .setParameter("userId", user.getId())
                .getResultList();

        if(list == null || list.isEmpty()) {
            return  null;
        }

        return list;
    }

    @Override
    public UserFavoriteGame findById(User user, Game game) {
        if(user == null || game == null) {
            return null;
        }
        List<UserFavoriteGame> list = em.createQuery("FROM UserFavoriteGame AS u WHERE u.user.id = :userId AND u.game.id = :gameId", UserFavoriteGame.class)
                .setParameter("userId", user.getId())
                .setParameter("gameId", game.getId())
                .getResultList();
        if(list == null || list.isEmpty()) {
            return null;
        }

        return list.get(0);
    }


}
