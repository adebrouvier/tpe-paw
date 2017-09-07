package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/*
    Form for creating new tournaments
 */
public class TournamentForm {

    @Size(min=4 , max=20)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String tournamentName;

    private String players;

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getPlayers() {
        return players;
    }

    public void setPlayers(String players) {
        this.players = players;
    }
}
