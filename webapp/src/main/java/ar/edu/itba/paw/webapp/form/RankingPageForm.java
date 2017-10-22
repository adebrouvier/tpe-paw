package ar.edu.itba.paw.webapp.form;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;

public class RankingPageForm {

    @Size(min=1)
    @NotNull
    @Valid
    private List<RankingTournaments> tournaments;

    public List<RankingTournaments> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<RankingTournaments> tournaments) {
        this.tournaments = tournaments;
    }
}
