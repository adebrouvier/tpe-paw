package ar.edu.itba.paw.model;


import javax.persistence.*;

@Entity
@Table(name = "user_follow")
@IdClass(UserFollowPK.class)
public class UserFollow {

  @Id
  @Column(name = "user_follower_id")
  private Long userFollowerId;

  @ManyToOne
  @JoinColumn(name = "user_follower_id", insertable = false, updatable = false)
  private User userFollower;

  @Id
  @Column(name = "user_followed_id")
  private Long userFollowedId;

  @ManyToOne
  @JoinColumn(name = "user_followed_id", insertable = false, updatable = false)
  private User userFollowed;

  public UserFollow() {
    /* for Hibernate */
  }

  public UserFollow(Long userFollowerId, Long userFollowedId) {
    this.userFollowerId = userFollowerId;
    this.userFollowedId = userFollowedId;
  }

  public Long getUserFollowerId() {
    return userFollowerId;
  }

  public void setUserFollowerId(Long userFollowerId) {
    this.userFollowerId = userFollowerId;
  }

  public User getUserFollower() {
    return userFollower;
  }

  public void setUserFollower(User userFollower) {
    this.userFollower = userFollower;
  }

  public Long getUserFollowedId() {
    return userFollowedId;
  }

  public void setUserFollowedId(Long userFollowedId) {
    this.userFollowedId = userFollowedId;
  }

  public User getUserFollowed() {
    return userFollowed;
  }

  public void setUserFollowed(User userFollowed) {
    this.userFollowed = userFollowed;
  }
}
