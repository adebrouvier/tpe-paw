package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.model.TopUserDTO;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class UserHibernateDao implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public User create(final String username, final String password) {
        final User user = new User(username, password);
        em.persist(user);
        return user;
    }

    @Override
    public List<TopUserDTO> findTopWinners(int top) {

        final TypedQuery<TopUserDTO> query = em.createQuery("select new ar.edu.itba.paw.model.TopUserDTO(p.user, count(*)) from Player as p where standing = 1 group by p.user", TopUserDTO.class);
        List<TopUserDTO> list = query.getResultList();
        list.sort(((tu1, tu2) -> tu2.getWins().intValue() - tu1.getWins().intValue()));
        return list;
    }

    @Override
    public User findByName(final String username) {
        final TypedQuery<User> query = em.createQuery("from User as u where u.name = :username", User.class);
        query.setParameter("username", username);
        Long unreadNotifications = em.createQuery("SELECT count(*) FROM Notification AS n WHERE n.user.name LIKE :username AND n.isRead = false", Long.class)
                .setParameter("username", username).getSingleResult();
        final List<User> list = query.getResultList();
        if(list.isEmpty()) {
            return null;
        }
        User u = list.get(0);
        u.setUnreadNotifications(unreadNotifications.intValue());
        return u;
    }

    @Override
    public User findById(long id) {
        return em.find(User.class, id);
    }

    @Transactional
    @Override
    public void updateDescription(User user, String description, String twitchUrl, String twitterUrl, String youtubeUrl) {
        if(user == null) {
            return;
        }
        user.setDescription(description);
        user.setTwitchUrl(addHttpsToUrl(twitchUrl));
        user.setTwitterUrl(addHttpsToUrl(twitterUrl));
        user.setYoutubeUrl(addHttpsToUrl(youtubeUrl));
        em.merge(user);
    }

    private String addHttpsToUrl(String url) {
        if(url == null || url.isEmpty()) {
            return null;
        }
        char c = url.toLowerCase().charAt(0);
        if(c == 'y' || c == 't' || c == 'w') {
            return "https://" + url;
        }
        return url;
    }
}