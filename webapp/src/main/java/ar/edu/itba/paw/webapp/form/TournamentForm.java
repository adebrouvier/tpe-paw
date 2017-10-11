package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.validation.PlayerConstraint;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/*
    Form for creating new tournaments
 */
public class TournamentForm {

    @Size(min = 4, max = 40)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String tournamentName;

    @Size(min = 0, max = 60)
    private String game;

    @Pattern(regexp = "(([a-zA-Z0-9]{1,25}\r\n)+([a-zA-Z0-9]{1,25})$)|([a-zA-Z0-9]{1,25}\r\n)+")
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

    public void setGame(String game) {
        this.game = game;
    }

    public String getGame() {
        return game;
    }
}
