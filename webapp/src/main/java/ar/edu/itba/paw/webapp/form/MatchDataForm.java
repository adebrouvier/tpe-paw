package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class MatchDataForm {

  @Size(min = 0, max = 40)
  @Pattern(regexp = "[a-zA-Z0-9 ,]+")
  private String map;

  @Size(min = 0, max = 40)
  @Pattern(regexp = "[a-zA-Z0-9]+")
  private String homePlayerCharacter;

  @Size(min = 0, max = 40)
  @Pattern(regexp = "[a-zA-Z0-9]+")
  private String awayPlayerCharacter;

  @Size(min = 0, max = 2000)
  @Pattern(regexp = "|(https://www.youtube.com/|https://youtube.com/|youtube.com/|www.youtube.com/)[a-zA-Z0-9@:!$&'()*+,;=%\\-._~\\[\\]/?\"#]*")
  private String vodLink;

  public String getAwayPlayerCharacter() {
    return awayPlayerCharacter;
  }

  public void setAwayPlayerCharacter(String awayPlayerCharacter) {
    this.awayPlayerCharacter = awayPlayerCharacter;
  }

  public String getHomePlayerCharacter() {
    return homePlayerCharacter;
  }

  public void setHomePlayerCharacter(String homePlayerCharacter) {
    this.homePlayerCharacter = homePlayerCharacter;
  }

  public String getMap() {
    return map;
  }

  public void setMap(String map) {
    this.map = map;
  }

  public String getVodLink() {
    return vodLink;
  }

  public void setVodLink(String vodLink) {
    this.vodLink = vodLink;
  }
}
