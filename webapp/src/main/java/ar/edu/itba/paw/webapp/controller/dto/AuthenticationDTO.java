package ar.edu.itba.paw.webapp.controller.dto;

public class AuthenticationDTO {

  private String username;

  private String password;

  public AuthenticationDTO() {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
