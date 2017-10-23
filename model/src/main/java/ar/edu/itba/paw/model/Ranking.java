package ar.edu.itba.paw.model;

import java.util.List;

public class Ranking {
    private long id;
    private List<UserScore> users;
    private List<TournamentPoints> tournaments;
    private String name;
    private long gameId;
    private Game game;

    public Ranking(long id, String name, long gameId){
        this.id = id;
        this.name = name;
        this.gameId = gameId;
    }

    public Game getGame(){
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGame(long gameId) {
        this.gameId = gameId;
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
