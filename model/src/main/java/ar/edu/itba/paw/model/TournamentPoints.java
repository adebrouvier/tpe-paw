package ar.edu.itba.paw.model;

public class TournamentPoints {

    private int tournamentId;
    private int awardedPoints;

    public TournamentPoints(int tournamentId,int awardedPoints){
        this.tournamentId = tournamentId;
        this.awardedPoints = awardedPoints;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(int awardedPoints) {
        this.awardedPoints = awardedPoints;
    }
}
