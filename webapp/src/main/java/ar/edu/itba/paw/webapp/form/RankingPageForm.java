package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RankingPageForm {

    @Size (min = 4, max = 40)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String tournamentName;

    @NotNull
    @Min(value = 10L)
    private Integer points;

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
