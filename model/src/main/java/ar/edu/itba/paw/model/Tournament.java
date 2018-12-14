package ar.edu.itba.paw.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournament")
public class Tournament {

  public enum Status {NEW, STARTED, FINISHED}

  /**
   * Id of the tournament
   */
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tournament_tournament_id_seq")
  @SequenceGenerator(sequenceName = "tournament_tournament_id_seq",
    name = "tournament_tournament_id_seq", allocationSize = 1)
  @Column(name = "tournament_id")
  private long id;

  /**
   * Name of the tournament
   */
  @Column(name = "name", length = 100, nullable = false)
  private String name;

  /**
   * Status of the tournament
   */
  @Enumerated(EnumType.STRING)
  private Status status;

  /**
   * List of all the players, without BYES
   */
  @OneToMany(fetch = FetchType.EAGER, orphanRemoval = false, mappedBy = "tournament")
  @OrderBy("seed ASC")
  private List<Player> players;

  /**
   * List of every match, including BYES
   */
  @OneToMany(fetch = FetchType.EAGER, orphanRemoval = false, mappedBy = "tournament")
  @OrderBy("id ASC")
  private List<Match> matches;

  /**
   * Game that the tournament hosts
   */
  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "game_id")
  private Game game;

  /**
   * User that created the tournament
   */
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "user_id")
  private User creator;

  @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
  @Fetch(FetchMode.SELECT)
  private List<Comment> comments;

  public Tournament(final String name, final Game game, Status status, final User creator) {
    this.players = new ArrayList<>();
    this.matches = new ArrayList<>();
    this.comments = new ArrayList<>();
    this.name = name;
    this.status = status;
    this.game = game;
    this.creator = creator;
  }

  Tournament() {
    /* For Hibernate */
  }

  public Game getGame() {
    return game;
  }

  public void setGame(Game game) {
    this.game = game;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Player> getPlayers() {
    return players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public List<Match> getMatches() {
    return matches;
  }

  public void setMatches(List<Match> matches) {
    this.matches = matches;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User user) {
    this.creator = user;
  }

  public int getFullSize() {
    return (int) Math.pow(2, Math.ceil((Math.log(players.size()) / Math.log(2))));
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public void addComment(Comment comment) {
    if (!comments.contains(comment)) {
      comments.add(comment);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Tournament that = (Tournament) o;

    return id == that.id;
  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }
}
