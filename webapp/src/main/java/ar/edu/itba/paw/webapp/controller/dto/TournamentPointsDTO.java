package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.TournamentPoints;

public class TournamentPointsDTO {

  public int awardedPoints;

  public long tournamentId;

  public String tournamentName;

  public TournamentPointsDTO() {
  }

  public TournamentPointsDTO(TournamentPoints tournamentPoints) {
    this.awardedPoints = tournamentPoints.getAwardedPoints();
    Tournament t = tournamentPoints.getTournament();
    this.tournamentId = t.getId();
    this.tournamentName = t.getName();
  }

  public int getAwardedPoints() {
    return awardedPoints;
  }

  public void setAwardedPoints(int awardedPoints) {
    this.awardedPoints = awardedPoints;
  }

  public long getTournamentId() {
    return tournamentId;
  }

  public void setTournamentId(long tournamentId) {
    this.tournamentId = tournamentId;
  }

  public String getTournamentName() {
    return tournamentName;
  }

  public void setTournamentName(String tournamentName) {
    this.tournamentName = tournamentName;
  }
}
