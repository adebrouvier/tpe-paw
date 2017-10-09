package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Tournament;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

public class RankingForm {

    @Size(min=4, max=20)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String rankingName;

    @Digits(integer=3,fraction = 0)
    private int tournamentId;

    @Digits(integer=3,fraction = 0)
    private int awardedPoints;

    public String getRankingName() {
        return rankingName;
    }

    public void setRankingName(String rankingName) {
        this.rankingName = rankingName;
    }

    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getAwardedPoints() {
        return awardedPoints;
    }

    public void setAwardedPoints(int awardedPoints) {
        this.awardedPoints = awardedPoints;
    }
}
