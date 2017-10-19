package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.List;

public class Tournament {

    /**
     * Name of the tournament
     */
    private String name;

    /**
     * Whether if the tournament finished or not
     */
    private boolean isFinished;

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

    public Tournament(String name, long id, long gameId, boolean isFinished, long userId){
        this.players = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.name = name;
        this.id = id;
        this.isFinished = isFinished;
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

    public boolean isFinished() { return isFinished; }

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
