package ar.edu.itba.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "game_url_image")
public class GameUrlImage {

  @Id
  @Column(name = "game_id")
  private Long gameId;

  @Column(name = "url_image")
  private String urlImage;

  public GameUrlImage() {
    /* for hibernate */
  }

  public GameUrlImage(Long gameId, String ulrImage) {
    this.gameId = gameId;
    this.urlImage = ulrImage;
  }

  public Long getGameId() {
    return gameId;
  }

  public void setGameId(Long gameId) {
    this.gameId = gameId;
  }

  public String getUrlImage() {
    return urlImage;
  }

  public void setUrlImage(String urlImage) {
    this.urlImage = urlImage;
  }
}
