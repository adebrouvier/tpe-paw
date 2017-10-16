package ar.edu.itba.paw.webapp.form;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

/*
    Form for creating new rankings
 */
public class RankingForm {

    @Size(min=4, max=20)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String rankingName;

    @Size(min=2)
    @NotNull
    @Valid
    private List<RankingTournaments> tournaments;

    public String getRankingName() {
        return rankingName;
    }

    public void setRankingName(String rankingName){
        this.rankingName=rankingName;
    }

    public List<RankingTournaments> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<RankingTournaments> tournaments) {
        this.tournaments = tournaments;
    }
}
