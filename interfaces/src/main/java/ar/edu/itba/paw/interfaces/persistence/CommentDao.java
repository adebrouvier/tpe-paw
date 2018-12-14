package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Comment;
import ar.edu.itba.paw.model.User;

import java.util.Date;

public interface CommentDao {

  /**
   * Creates a comment
   *
   * @param creator user that commented
   * @param date    date of the comment
   * @param comment the comment itself
   * @return the created comment
   */
  Comment create(User creator, Date date, String comment);

  /**
   * Finds a comment by id
   *
   * @param id id of the comment
   * @return the comment
   */
  Comment findById(long id);

  Comment create(User creator, Date date, String comment, Comment parent);
}
