package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.CommentDao;
import ar.edu.itba.paw.model.Comment;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

@Repository
public class CommentHibernateDao implements CommentDao{

    @PersistenceContext
    private EntityManager em;

    public Comment create(User creator, Date date, String comment){
        final Comment c = new Comment(creator, date, comment);
        em.persist(c);
        return c;
    }

    @Override
    public Comment findById(long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public Comment create(User creator, Date date, String comment, Comment parent) {

        final Comment c = new Comment(creator, date, comment, parent);
        em.persist(c);
        return c;
    }

}
