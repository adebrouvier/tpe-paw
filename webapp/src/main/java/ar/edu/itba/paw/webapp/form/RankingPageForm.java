package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.*;

public class RankingPageForm {

    @Size (min = 4, max = 40)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String tournamentName;

    @NotNull
    @Min(value = 10L)
    @Digits(integer = 5, fraction = 0)
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
