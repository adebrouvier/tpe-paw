package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Digits;

public class MatchForm {

    @Digits(integer = 3,fraction = 0)
    private int homeResult;

    @Digits(integer = 3,fraction = 0)
    private int awayResult;

    public int getHomeResult() {
        return homeResult;
    }

    public void setHomeResult(int homeResult) {
        this.homeResult = homeResult;
    }

    public int getAwayResult() {
        return awayResult;
    }

    public void setAwayResult(int awayResult) {
        this.awayResult = awayResult;
    }
}
