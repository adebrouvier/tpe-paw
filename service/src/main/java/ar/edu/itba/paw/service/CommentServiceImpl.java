package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.CommentDao;
import ar.edu.itba.paw.interfaces.service.CommentService;
import ar.edu.itba.paw.model.Comment;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    private CommentDao commentDao;

    @Override
    @Transactional
    public Comment create(User creator, Date date, String comment) {
        return commentDao.create(creator, date, comment);
    }

    @Override
    public Comment findById(long id) {
        return commentDao.findById(id);
    }
}
