package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class TournamentDTO {

  private Long id;

  private String name;

  private String status;

  private List<PlayerDTO> players;

  private List<MatchDTO> matches;

  private GameDTO game;

  private UserDTO creator;

  public TournamentDTO(){}

  public TournamentDTO(Tournament tournament){
    this.id = tournament.getId();
    this.name = tournament.getName();
    this.status = tournament.getStatus().toString();
    this.players = tournament.getPlayers().stream()
                        .map(PlayerDTO::new)
                        .collect(Collectors.toList());
    this.matches = tournament.getMatches().stream()
      .map(MatchDTO::new)
      .collect(Collectors.toList());
    this.game = new GameDTO(tournament.getGame());
    this.creator = new UserDTO(tournament.getCreator());
  }

  public String getName() {
    return name;
  }

  public String getStatus() {
    return status;
  }

  public List<PlayerDTO> getPlayers() {
    return players;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public void setPlayers(List<PlayerDTO> players) {
    this.players = players;
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
}
