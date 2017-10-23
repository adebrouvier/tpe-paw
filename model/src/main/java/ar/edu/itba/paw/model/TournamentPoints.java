package ar.edu.itba.paw.model;

public class TournamentPoints {

    private long tournamentId;
    private int awardedPoints;
    private String name;

    public TournamentPoints(long tournamentId,int awardedPoints){
        this.tournamentId = tournamentId;
        this.awardedPoints = awardedPoints;
    }

    public long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(int awardedPoints) {
        this.awardedPoints = awardedPoints;
    }
}
