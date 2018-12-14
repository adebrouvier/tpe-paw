package ar.edu.itba.paw.model;

import java.io.Serializable;

public class InscriptionPK implements Serializable {

  private Tournament tournament;
  private User user;

  public InscriptionPK() {

  }

  public InscriptionPK(User user, Tournament tournament) {
    this.user = user;
    this.tournament = tournament;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    InscriptionPK that = (InscriptionPK) o;

    if (!tournament.equals(that.tournament)) return false;
    return user.equals(that.user);
  }

  @Override
  public int hashCode() {
    int result = tournament.hashCode();
    result = 31 * result + user.hashCode();
    return result;
  }
}
