package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "player")
public class Player {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_player_id_seq")
  @SequenceGenerator(sequenceName = "player_player_id_seq",
    name = "player_player_id_seq", allocationSize = 1)
  @Column(name = "player_id")
  private Long id;

  @Column(name = "name", length = 25, nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.EAGER, optional = true)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "tournament_id")
  private Tournament tournament;

  @Column(name = "seed")
  private Integer seed;

  @Column(name = "standing")
  private Integer standing;

  Player() {
    /* For Hibernate */
  }

  public Player(final String name, final Tournament tournament) {
    this.name = name;
    this.tournament = tournament;
  }

  public Player(final String name, final User user, Tournament tournament) {
    this.name = name;
    this.user = user;
    this.tournament = tournament;
  }

  public boolean hasUser() {
    return user != null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getSeed() {
    return seed;
  }

  public void setSeed(Integer seed) {
    this.seed = seed;
  }

  public Integer getStanding() {
    return standing;
  }

  public void setStanding(Integer standing) {
    this.standing = standing;
  }

  public Tournament getTournament() {
    return this.tournament;
  }

  public void setTournament(Tournament tournament) {
    this.tournament = tournament;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Player player = (Player) o;

    return Objects.equals(id, player.id);
  }

  @Override
  public int hashCode() {
    return (int) (id ^ (id >>> 32));
  }

  @Override
  public String toString() {
    return name + " - " + "Id: " + id;
  }

}
