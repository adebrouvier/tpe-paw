package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.Comment;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class CommentDTO {

  private long id;

  private UserDTO creator;

  private Date date;

  private String comment;

  private List<CommentDTO> children;

  public CommentDTO() {
  }

  public CommentDTO(Comment comment) {
    this.id = comment.getId();
    this.creator = new UserDTO(comment.getCreator());
    this.date = comment.getDate();
    this.comment = comment.getComment();
    this.children = comment.getChildren().stream().map(CommentDTO::new).collect(Collectors.toList());
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public UserDTO getCreator() {
    return creator;
  }

  public void setCreator(UserDTO creator) {
    this.creator = creator;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }

  public List<CommentDTO> getChildren() {
    return children;
  }

  public void setChildren(List<CommentDTO> children) {
    this.children = children;
  }
}
