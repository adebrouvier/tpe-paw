package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.UserScore;

public class UserScoresDTO {

  private int score;

  private UserDTO user;

  public UserScoresDTO() {
  }

  public UserScoresDTO(UserScore userScore) {
    this.score = userScore.getPoints();
    this.user = new UserDTO(userScore.getUser());
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }
}
