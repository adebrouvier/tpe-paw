package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.Game;

public class GameDTO {

  private long id;

  private String name;

  public GameDTO(){
  }

  public GameDTO(Game game) {
    this.id = game.getId();
    this.name = game.getName();
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
}
