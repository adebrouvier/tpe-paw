package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/*
    Form for searching tournaments
 */
public class TournamentSearchForm {

    @Size(max = 40)
    @Pattern(regexp = "[a-zA-Z0-9 ]+")
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
