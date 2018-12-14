package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.Tournament;

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

  public TournamentDTO() {
  }

  public TournamentDTO(Tournament tournament) {
    this.id = tournament.getId();
    this.name = tournament.getName();
    this.status = tournament.getStatus().toString();
    this.players = tournament.getPlayers().stream()
      .map(PlayerDTO::new)
      .collect(Collectors.toList());
    this.matches = tournament.getMatches().stream()
      .map(MatchDTO::new)
      .collect(Collectors.toList());
    if (tournament.getGame() != null) {
      this.game = new GameDTO(tournament.getGame());
    }
    this.creator = new UserDTO(tournament.getCreator());
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public List<MatchDTO> getMatches() {
    return matches;
  }

  public void addMatch(MatchDTO match) {
    this.matches.add(match);
  }

  public void setMatches(List<MatchDTO> matches) {
    this.matches = matches;
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

  public void addPlayer(PlayerDTO player) {
    this.players.add(player);
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
