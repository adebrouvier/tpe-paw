package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "user_favorite_game")
@IdClass(UserFavoriteGamePK.class)
public class UserFavoriteGame {

  @Id
  @Column(name = "user_id")
  private Long userId;

  @Id
  @Column(name = "game_id")
  private Long gameId;

  @ManyToOne
  @JoinColumn(name = "user_id", insertable = false, updatable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "game_id", insertable = false, updatable = false)
  private Game game;

  public UserFavoriteGame() {
    /* for Hibernate */
  }

  public UserFavoriteGame(Long userId, Long gameId, User user, Game game) {
    this.userId = userId;
    this.gameId = gameId;
    this.user = user;
    this.game = game;
  }

  public UserFavoriteGame(Long userId, Long gameId) {
    this.userId = userId;
    this.gameId = gameId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public Long getGameId() {
    return gameId;
  }

  public void setGameId(Long gameId) {
    this.gameId = gameId;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }
}
