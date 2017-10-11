package ar.edu.itba.paw.webapp.form;

import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.form.validation.PlayerConstraint;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;

public class RankingForm {

    @Size(min=4, max=20)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String rankingName;

    @Pattern(regexp = "(([a-zA-Z0-9]{1,50}\r\n)+([a-zA-Z0-9]{1,50})$)|([a-zA-Z0-9]{1,50}\r\n)+")
    @PlayerConstraint
    private String tournaments;

    public String getRankingName() {
        return rankingName;
    }

    public String getTournaments() {
        return tournaments;
    }

    public void setTournaments(String tournaments) {
        this.tournaments = tournaments;
    }

    public void setRankingName(String rankingName) {
        this.rankingName = rankingName;
    }
/*
    public int getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(int tournamentId) {
        this.tournamentId = tournamentId;
    }

    public int getAwardedPoints() {
        return awardedPoints;
    }*/
/*
    public void setAwardedPoints(int awardedPoints) {
        this.awardedPoints = awardedPoints;
    }*/
}
