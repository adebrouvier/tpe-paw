package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Form for registering users
 */
public class RegisterForm {

  @Size(min = 6, max = 16)
  @Pattern(regexp = "[a-zA-Z0-9]+")
  private String username;

  @Size(min = 6, max = 100)
  @Pattern(regexp = "[a-zA-Z0-9]+")
  private String password;

  @Size(min = 6, max = 100)
  @Pattern(regexp = "[a-zA-Z0-9]+")
  private String repeatPassword;

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getRepeatPassword() {
    return repeatPassword;
  }

  public void setRepeatPassword(String repeatPassword) {
    this.repeatPassword = repeatPassword;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setUsername(String username) {
    this.username = username;
  }
}
