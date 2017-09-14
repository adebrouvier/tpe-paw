package ar.edu.itba.paw.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Tournament {
    private String name;
    private ArrayList<Player> players; /*TODO: If the total number is not a power of 2, it should be filled with BYES*/
    private long id;
    private int size;

    public Tournament(String name, long id){
        this.players = new ArrayList<>();
        this.name = name;
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
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

    public void setSize(int size) {
        this.size = size;
    }


    public boolean addPlayer(Player player){
        return players.add(player);
    }

    public boolean addPlayer(List<Player> players){
        return this.players.addAll(players);
    }

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
