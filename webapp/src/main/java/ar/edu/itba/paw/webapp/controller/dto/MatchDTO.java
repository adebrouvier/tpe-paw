package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.Match;

public class MatchDTO {

  private long id;

  private PlayerDTO homePlayer;

  private PlayerDTO awayPlayer;

  private int homePlayerScore;

  private int awayPlayerScore;

  private Boolean nextMatchHome;

  private String videoOnDemandLink;

  private String map;

  private String homePlayerCharacter;

  private String awayPlayerCharacter;

  private int standing;

  public MatchDTO(){
  }

  public MatchDTO(Match match){
    this.id = match.getId();
    if (match.getHomePlayer() != null)
      this.homePlayer = new PlayerDTO(match.getHomePlayer());
    if (match.getAwayPlayer() != null)
      this.awayPlayer = new PlayerDTO(match.getAwayPlayer());
    if (match.getHomePlayerScore() != null)
      this.homePlayerScore = match.getHomePlayerScore();
    if (match.getAwayPlayerScore() != null)
      this.awayPlayerScore = match.getAwayPlayerScore();
    this.nextMatchHome = match.isNextMatchHome();
    this.videoOnDemandLink = match.getVideoOnDemandLink();
    this.map = match.getMap();
    this.homePlayerCharacter = match.getHomePlayerCharacter();
    this.awayPlayerCharacter = match.getAwayPlayerCharacter();
    this.standing = match.getStanding();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public PlayerDTO getHomePlayer() {
    return homePlayer;
  }

  public void setHomePlayer(PlayerDTO homePlayer) {
    this.homePlayer = homePlayer;
  }

  public PlayerDTO getAwayPlayer() {
    return awayPlayer;
  }

  public void setAwayPlayer(PlayerDTO awayPlayer) {
    this.awayPlayer = awayPlayer;
  }

  public int getHomePlayerScore() {
    return homePlayerScore;
  }

  public void setHomePlayerScore(int homePlayerScore) {
    this.homePlayerScore = homePlayerScore;
  }

  public int getAwayPlayerScore() {
    return awayPlayerScore;
  }

  public void setAwayPlayerScore(int awayPlayerScore) {
    this.awayPlayerScore = awayPlayerScore;
  }

  public Boolean getNextMatchHome() {
    return nextMatchHome;
  }

  public void setNextMatchHome(Boolean nextMatchHome) {
    this.nextMatchHome = nextMatchHome;
  }

  public String getVideoOnDemandLink() {
    return videoOnDemandLink;
  }

  public void setVideoOnDemandLink(String videoOnDemandLink) {
    this.videoOnDemandLink = videoOnDemandLink;
  }

  public String getMap() {
    return map;
  }

  public void setMap(String map) {
    this.map = map;
  }

  public String getHomePlayerCharacter() {
    return homePlayerCharacter;
  }

  public void setHomePlayerCharacter(String homePlayerCharacter) {
    this.homePlayerCharacter = homePlayerCharacter;
  }

  public String getAwayPlayerCharacter() {
    return awayPlayerCharacter;
  }

  public void setAwayPlayerCharacter(String awayPlayerCharacter) {
    this.awayPlayerCharacter = awayPlayerCharacter;
  }

  public int getStanding() {
    return standing;
  }

  public void setStanding(int standing) {
    this.standing = standing;
  }
}
