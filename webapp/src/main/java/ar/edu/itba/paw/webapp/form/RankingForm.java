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

    @Size(min=4, max=20)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String game;


    public String getRankingName() {
        return rankingName;
    }

    public void setRankingName(String rankingName){
        this.rankingName=rankingName;
    }


    public String getGame() {
        return game;
    }

    public void setGame(String game){this.game = game;}
}
