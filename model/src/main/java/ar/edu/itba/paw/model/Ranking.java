package ar.edu.itba.paw.model;

import java.util.List;
import java.util.Map;

public class Ranking {
    private long id;
    private List<PlayerScores> players;
    private List<Tournament> tournaments; /*Maybe we only need TournamentIds*/
    private int pointsAwarded;
    private String name;

    public Ranking(long id,int pointsAwarded, String name){
        this.id = id;
        this.pointsAwarded = pointsAwarded;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public List<PlayerScores> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerScores> players) {
        this.players = players;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public int getPointsAwarded() {
        return pointsAwarded;
    }

    public void setPointsAwarded(int pointsAwarded) {
        this.pointsAwarded = pointsAwarded;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
