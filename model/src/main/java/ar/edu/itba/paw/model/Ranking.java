package ar.edu.itba.paw.model;

import java.util.List;

public class Ranking {
    private long id;
    private List<UserScore> users;
    private List<TournamentPoints> tournaments;
    private String name;

    public Ranking(long id, String name){
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public List<UserScore> getUsers() {
        return users;
    }

    public void setUsers(List<UserScore> users) {
        this.users = users;
    }

    public List<TournamentPoints> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<TournamentPoints> tournaments) {
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
