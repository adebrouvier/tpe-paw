package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.validation.PlayerConstraint;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/*
    Form for creating new tournaments
 */
public class TournamentForm {

    @Size(min = 4, max = 20)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String tournamentName;

    @Pattern(regexp = "[a-zA-Z0-9_\r\n]+")
    @PlayerConstraint
    private String players;

    private boolean randomizeSeed;

    public boolean isRandomizeSeed() { return randomizeSeed; }

    public void setRandomizeSeed(boolean randomizeSeed) { this.randomizeSeed = randomizeSeed; }

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
