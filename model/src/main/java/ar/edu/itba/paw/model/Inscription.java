package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "inscription")
@IdClass(InscriptionPK.class)
public class Inscription {

  @Id
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Id
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "tournament_id")
  private Tournament tournament;

  Inscription() {
    /*For Hibernate*/
  }

  public Inscription(User user, Tournament tournament) {
    this.user = user;
    this.tournament = tournament;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Tournament getTournament() {
    return tournament;
  }

  public void setTournament(Tournament tournament) {
    this.tournament = tournament;
  }
}
