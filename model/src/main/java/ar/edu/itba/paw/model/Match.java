package ar.edu.itba.paw.model;

import com.sun.org.apache.bcel.internal.generic.LNEG;

/**
 * Created by marcos on 09/09/17.
 */
public class Match {

    private Long localPlayerId;
    private Long visitorPlayerId;
    private Integer localPlayerScore;
    private Integer visitorPlayerScore;
    private Integer name;
    private Long id;
    private Long tournamentId;

    public Match(Long localPlayerId, Long visitorPlayerId, Integer localPlayerScore, Integer visitorPlayerScore, Integer name, Long id, Long tournamentId) {
        this.localPlayerId = localPlayerId;
        this.visitorPlayerId = visitorPlayerId;
        this.localPlayerScore = localPlayerScore;
        this.visitorPlayerScore = visitorPlayerScore;
        this.name = name;
        this.id = id;
        this.tournamentId = tournamentId;
    }

    public Match(Integer name, Long id, Long tournamentId) {
        this.name = name;
        this.id = id;
        this.tournamentId = tournamentId;
    }

    public Long getLocalPlayer() {
        return localPlayerId;
    }

    public Long getVisitorPlayer() {
        return visitorPlayerId;
    }

    public Integer getLocalPlayerScore() {
        return localPlayerScore;
    }

    public Integer getVisitorPlayerScore() {
        return visitorPlayerScore;
    }

    public Integer getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public Long getTournamentId() {
        return tournamentId;
    }
}
