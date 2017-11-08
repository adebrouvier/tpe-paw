package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.UserImageDao;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserImage;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class UserImageHibernateDao implements UserImageDao{

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @Override
    public void updateImage(User user, byte[] image) {
        UserImage ui = findById(user.getId());
        if(ui == null) {
            ui = new UserImage(user.getId(), image);
            em.persist(ui);
        } else {
            ui.setImage(image);
            em.merge(ui);
        }
    }

    @Override
    public UserImage findById(long id) {
        return em.find(UserImage.class, id);
    }
}
