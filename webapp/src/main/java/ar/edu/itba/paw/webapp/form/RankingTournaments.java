package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RankingTournaments {

    @Size (min = 4, max = 40)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String name;

    @Min (value = 10L)
    private Integer points;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
