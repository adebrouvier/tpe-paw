package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.List;

public class Tournament {

    public enum Status {NEW, STARTED, FINISHED}

    /**
     * Name of the tournament
     */
    private String name;

    /**
     * Status of the tournament
     */
    private Status status;

    /**
     * List of all the players, including BYES
     */
    private List<Player> players;
    //TODO Players should have their standing

    /**
     * List of every match, including BYES
     */
    private List<Match> matches;

    /**
     * Count of matches, without BYES
     */
    private int numberOfMatches;

    /**
     * Id of the game that the tournament hosts
     */
    private long gameId;

    /**
     * Id of the user that created the tournament
     */
    private long userId;

    /**
     * Number of players, without counting byes
     */
    private int size;

    /**
     * Id of the tournament
     */
    private long id;

    public Tournament(String name, long id, long gameId, Status status, long userId){
        this.players = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.name = name;
        this.id = id;
        this.status = status;
        this.gameId = gameId;
        this.userId = userId;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
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

    public void addPlayer(List<Player> players){
        this.players.addAll(players);
    }

    public void removePlayer(List<Player> players) {
        for(Player player: players) {
            if(this.getPlayers().contains(player)) {
                this.getPlayers().remove(player);
            }
        }
    }

    public void addMatch(List<Match> matches){
        this.matches.addAll(matches);
    }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

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
