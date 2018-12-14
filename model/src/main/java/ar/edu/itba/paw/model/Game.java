package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_game_id_seq")
  @SequenceGenerator(sequenceName = "game_game_id_seq",
    name = "game_game_id_seq", allocationSize = 1)
  @Column(name = "game_id")
  private Long id;

  @Column(name = "name", length = 60, unique = true, nullable = false)
  private String name;

  @OneToOne
  @JoinColumn(name = "game_id")
  private GameImage gameImage;

  @OneToOne
  @JoinColumn(name = "game_id")
  private GameUrlImage gameUrlImage;

  @Column(name = "user_generated")
  private Boolean userGenerated;

  Game() {
    /* For Hibernate */
  }

  public Game(String name, Boolean userGenerated) {
    this.name = name;
    this.userGenerated = userGenerated;
  }

  public GameImage getGameImage() {
    return gameImage;
  }

  public void setGameImage(GameImage gameImage) {
    this.gameImage = gameImage;
  }

  public GameUrlImage getGameUrlImage() {
    return gameUrlImage;
  }

  public void setGameUrlImage(GameUrlImage gameUrlImage) {
    this.gameUrlImage = gameUrlImage;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getUserGenerated() {
    return userGenerated;
  }

  public void setUserGenerated(Boolean userGenerated) {
    this.userGenerated = userGenerated;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Game)) return false;

    Game game = (Game) o;

    return id.equals(game.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
