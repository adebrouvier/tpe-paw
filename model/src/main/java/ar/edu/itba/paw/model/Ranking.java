package ar.edu.itba.paw.model;

import java.util.List;
import java.util.Map;

public class Ranking {
    private long id;
    private List<PlayerScores> players;
    private Map<Tournament, Integer> tournaments; /*Maybe we only need TournamentIds*/
    private String name;

    public Ranking(long id, String name){
        this.id = id;
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

    public Map<Tournament, Integer> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Map<Tournament, Integer> tournaments) {
        this.tournaments = tournaments;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
