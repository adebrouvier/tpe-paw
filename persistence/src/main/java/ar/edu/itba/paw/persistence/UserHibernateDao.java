package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserDao;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
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
    public User findByName(final String username) {
        final TypedQuery<User> query = em.createQuery("from User as u where u.name = :username", User.class);
        query.setParameter("username", username);
        final List<User> list = query.getResultList();
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public User findById(long id) {
        return em.find(User.class, id);
    }

    @Transactional
    @Override
    public void updateDescription(User user, String description) {
        if(user == null) {
            return;
        }
        user.setDescription(description);
        em.merge(user);
    }
}