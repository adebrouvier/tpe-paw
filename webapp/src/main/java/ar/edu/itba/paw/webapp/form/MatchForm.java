package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;

/*
    Form for updating match results
 */
public class MatchForm {

    @Min(value = 0L)
    @Digits(integer = 3,fraction = 0)
    private int homeResult;

    @Min(value = 0L)
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
