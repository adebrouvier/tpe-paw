package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.MostFollowedDTO;

public class MostFollowedRESTDTO {

  private UserDTO user;
  private Long followers;

  public MostFollowedRESTDTO(){}

  public MostFollowedRESTDTO(MostFollowedDTO mostFollowedDTO) {
    this.user = new UserDTO(mostFollowedDTO.getUser());
    this.followers = mostFollowedDTO.getFollowers();
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public Long getFollowers() {
    return followers;
  }

  public void setFollowers(Long followers) {
    this.followers = followers;
  }
}
