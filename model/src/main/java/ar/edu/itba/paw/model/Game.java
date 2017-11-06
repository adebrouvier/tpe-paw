package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "game")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_game_id_seq")
    @SequenceGenerator(sequenceName = "game_game_id_seq",
            name = "game_game_id_seq", allocationSize = 1)
    @Column(name = "game_id")
    private long id;

    @Column(name = "name", length = 60, unique = true, nullable = false)
    private String name;

    @OneToOne
    private GameImage gameImage;

    @OneToOne
    private GameUrlImage gameUrlImage;

    @Column(name = "user_generated")
    private Boolean userGenerated;

    Game (){
        /* For Hibernate */
    }

    public Game(String name, Boolean userGenerated) {
        this.name = name;
        this.userGenerated = userGenerated;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
