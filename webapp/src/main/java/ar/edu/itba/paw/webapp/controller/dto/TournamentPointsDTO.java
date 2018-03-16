package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.TournamentPoints;

public class TournamentPointsDTO {

  public int awardedPoints;

  public long tournament;

  public TournamentPointsDTO(){
  }

  public TournamentPointsDTO(TournamentPoints tournamentPoints){
    this.awardedPoints = tournamentPoints.getAwardedPoints();
    this.tournament = tournamentPoints.getTournament().getId();
  }

  public int getAwardedPoints() {
    return awardedPoints;
  }

  public void setAwardedPoints(int awardedPoints) {
    this.awardedPoints = awardedPoints;
  }

  public long getTournament() {
    return tournament;
  }

  public void setTournament(long tournament) {
    this.tournament = tournament;
  }
}
