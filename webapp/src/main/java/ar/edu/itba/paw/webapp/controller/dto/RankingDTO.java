package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.Ranking;

import java.util.List;
import java.util.stream.Collectors;

public class RankingDTO {

  private long id;

  private String name;

  private GameDTO game;

  private UserDTO creator;

  private List<UserScoresDTO> userScores;

  private List<TournamentPointsDTO> tournamentPoints;

  public RankingDTO(){
  }

  public RankingDTO(Ranking ranking){
    this.id = ranking.getId();
    this.name = ranking.getName();
    this.game = new GameDTO(ranking.getGame());
    this.creator = new UserDTO(ranking.getUser());
    if (ranking.getUserScores() != null)
      this.userScores = ranking.getUserScores().stream()
        .map(UserScoresDTO::new)
        .collect(Collectors.toList());
    if (ranking.getTournaments() != null)
      this.tournamentPoints = ranking.getTournaments().stream()
        .map(TournamentPointsDTO::new)
        .collect(Collectors.toList());
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

  public GameDTO getGame() {
    return game;
  }

  public void setGame(GameDTO game) {
    this.game = game;
  }

  public UserDTO getCreator() {
    return creator;
  }

  public void setCreator(UserDTO creator) {
    this.creator = creator;
  }

  public List<UserScoresDTO> getUserScores() {
    return userScores;
  }

  public void setUserScores(List<UserScoresDTO> userScores) {
    this.userScores = userScores;
  }

  public List<TournamentPointsDTO> getTournamentPoints() {
    return tournamentPoints;
  }

  public void setTournamentPoints(List<TournamentPointsDTO> tournamentPoints) {
    this.tournamentPoints = tournamentPoints;
  }
}
