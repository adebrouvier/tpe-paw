package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournament")
public class Tournament {

    public enum Status {NEW, STARTED, FINISHED}

    /**
     * Id of the tournament
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tournament_tournament_id_seq")
    @SequenceGenerator(sequenceName = "tournament_tournament_id_seq",
            name = "tournament_tournament_id_seq", allocationSize = 1)
    @Column(name = "tournament_id")
    private long id;

    /**
     * Name of the tournament
     */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /**
     * Status of the tournament
     */
    @Enumerated(EnumType.STRING)
    private Status status;

    /**
     * List of all the players, including BYES
     */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "tournament")
    private List<Player> players;

    /**
     * Number of players, without counting byes
     */
    private int size;

    /**
     * List of every match, including BYES
     */
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = false, mappedBy = "tournament")
    private List<Match> matches;

    /**
     * Count of matches, without BYES
     */
    private int numberOfMatches;

    /**
     * Game that the tournament hosts
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Game game;

    /**
     * User that created the tournament
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    public Tournament(final String name, final Long id, final Game game, Status status, final User user){
        this.players = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.name = name;
        this.id = id;
        this.status = status;
        this.game = game;
        this.user = user;
    }

    public Tournament (){
        /* For Hibernate */
    }

    public Game getGame() {
        return game;
    }

    public void setGame(long gameId) {
        this.game = game;
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

    public void addMatch(List<Match> matches){
        this.matches.addAll(matches);
    }

    public Status getStatus() { return status; }

    public void setStatus(Status status) { this.status = status; }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
