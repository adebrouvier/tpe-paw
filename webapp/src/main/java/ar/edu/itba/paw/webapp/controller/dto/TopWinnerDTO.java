package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.TopUserDTO;

public class TopWinnerDTO {

  private UserDTO user;
  private Long wins;

  public TopWinnerDTO() {
  }

  public TopWinnerDTO(TopUserDTO topUserDTO) {
    this.user = new UserDTO(topUserDTO.getUser());
    this.wins = topUserDTO.getWins();
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public Long getWins() {
    return wins;
  }

  public void setWins(Long wins) {
    this.wins = wins;
  }
}
