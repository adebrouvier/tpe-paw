package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.Player;

public class PlayerDTO {

  private long id;

  private String name;

  private UserDTO user;

  private int seed;

  private int standing;

  public PlayerDTO(){
  }

  public PlayerDTO(Player player){
    this.id = player.getId();
    this.name = player.getName();
    this.user = new UserDTO(player.getUser());
    this.seed = player.getSeed();
    this.standing = player.getStanding();

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

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public int getSeed() {
    return seed;
  }

  public void setSeed(int seed) {
    this.seed = seed;
  }

  public int getStanding() {
    return standing;
  }

  public void setStanding(int standing) {
    this.standing = standing;
  }
}
