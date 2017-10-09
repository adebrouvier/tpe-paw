package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.List;

public class Tournament {
    private String name;
    private Boolean isFinished;
    private int tier;
    private List<Player> players;
    private List<Match> matches;
    private int numberOfMatches;

    private int size;

    private long id;

    public Tournament(String name, long id){
        this.players = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.name = name;
        this.id = id;
        this.tier = 1;
        this.isFinished = false;
    }

    public Tournament(String name, long id, int tier){
        this.players = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.name = name;
        this.id = id;
        this.tier = tier;
        this.isFinished = false;
    }

    public Tournament(String name, long id, boolean isFinished, int tier){
        this.players = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.name = name;
        this.id = id;
        this.tier = tier;
        this.isFinished = isFinished;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size){
        this.size = size;
    }

    public int getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(int numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }

    public boolean addPlayer(Player player){
        return players.add(player);
    }

    public boolean addPlayer(List<Player> players){
        return this.players.addAll(players);
    }

    public boolean addMatch(Match match){
        return matches.add(match);
    }

    public boolean addMatch(List<Match> matches){
        return this.matches.addAll(matches);
    }

    public void endTournament() { this.isFinished = true; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tournament that = (Tournament) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
