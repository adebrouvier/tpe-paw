package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.Game;

public class GameDTO {

  private long id;

  private String name;

  private String gameUrl;

  public GameDTO(){
  }

  public GameDTO(Game game) {
    this.id = game.getId();
    this.name = game.getName();
    if(game.getGameUrlImage() != null) {
      this.gameUrl = game.getGameUrlImage().getUrlImage();
    }
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getGameUrl() {
    return gameUrl;
  }

  public void setGameUrl(String gameUrl) {
    this.gameUrl = gameUrl;
  }
}
