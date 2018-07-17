package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/*
        Form for creating new tournaments
 */
public class TournamentForm {

    @NotNull
    @Size(min = 4, max = 40)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String name;

    @Size(min = 0, max = 60)
    private String game;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public String getGame() {
        return game;
    }
}
