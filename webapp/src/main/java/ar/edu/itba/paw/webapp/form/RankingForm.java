package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class RankingForm {

    @Size(min=4, max=20)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String rankingName;

    private List<String> tournaments;
    private List<Integer> points;

    public String getRankingName() {
        return rankingName;
    }

    public void setRankingName(String rankingName){
        this.rankingName=rankingName;
    }

    public List<String> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<String> tournaments) {
        this.tournaments = tournaments;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public void setPoints(List<Integer> points) {
        this.points = points;
    }
}
