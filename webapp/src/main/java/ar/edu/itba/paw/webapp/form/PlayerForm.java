package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.webapp.form.validation.ExistingUser;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class PlayerForm {

    @Size (min = 4, max = 16)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    @NotNull
    private String player;

    @Size (min = 6, max = 40)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    @ExistingUser
    private String username;

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
