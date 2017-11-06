package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.List;

@Table(name = "ranking")
public class Ranking {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ranking_ranking_id_seq")
    @SequenceGenerator(sequenceName = "ranking_ranking_id_seq",
            name = "ranking_ranking_id_seq", allocationSize = 1)
    @Column(name = "ranking_id")
    private Long id;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Game game;

    /**
     * User that created the Ranking
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ranking")
    private List<UserScore> userScores;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ranking")
    private List<TournamentPoints> tournaments;

    Ranking (){
        /* For Hibernate */
    }

    public Ranking(String name, Game game, User user) {
        this.name = name;
        this.game = game;
        this.user = user;
    }

    public Game getGame(){
        return game;
    }

    public void setGame(Game game){
        this.game = game;
    }

    public long getId() {
        return id;
    }

    public List<UserScore> getUsers() {
        return userScores;
    }

    public void setUsers(List<UserScore> userScores) {
        this.userScores = userScores;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
