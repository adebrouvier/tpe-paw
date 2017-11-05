package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "ranking_tournaments")
@SecondaryTable(name="tournament", pkJoinColumns={
        @PrimaryKeyJoinColumn(name="tournament_id", referencedColumnName="tournament_id")})
public class TournamentPoints {

    @Column(name = "awarded_points")
    private int awardedPoints;

    @Column(name = "ranking_id")
    private long rankingId;

    @Column(name = "tournament_id")
    private long tournamentId;

    @Column(table = "tournament", name = "name")
    private String tournamentName;

    public TournamentPoints() {
        /* for Hibernate */
    }

    public TournamentPoints(long rankingId,long tournamentId,int awardedPoints){
        this.rankingId = rankingId;
        this.tournamentId = tournamentId;
        this.awardedPoints = awardedPoints;
    }

    public long getRankingId() {
        return rankingId;
    }

    public void setRankingId(long rankingId) {
        this.rankingId = rankingId;
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

    public int getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(int awardedPoints) {
        this.awardedPoints = awardedPoints;
    }
}
