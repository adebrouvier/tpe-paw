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
    private Integer matchId;
    private Integer nextMatchId;
    private Long tournamentId;

    public Match(Long localPlayerId, Long visitorPlayerId, Integer matchId, Integer nextMatchId, Long tournamentId) {
        this.localPlayerId = localPlayerId;
        this.visitorPlayerId = visitorPlayerId;
        this.matchId = matchId;
        this.nextMatchId = nextMatchId;
        this.tournamentId = tournamentId;
    }

    public Match(Integer matchId, Integer nextMatchId, Long tournamentId) {
        this.matchId = matchId;
        this.nextMatchId = nextMatchId;
        this.tournamentId = tournamentId;
    }

    public Match(Long localPlayerId, Long visitorPlayerId, Integer localPlayerScore, Integer visitorPlayerScore, Integer matchId, Integer nextMatchId, Long tournamentId) {
        this.localPlayerId = localPlayerId;
        this.visitorPlayerId = visitorPlayerId;
        this.localPlayerScore = localPlayerScore;
        this.visitorPlayerScore = visitorPlayerScore;
        this.matchId = matchId;
        this.nextMatchId = nextMatchId;
        this.tournamentId = tournamentId;
    }

    public Long getLocalPlayerId() {
        return localPlayerId;
    }

    public Long getVisitorPlayerId() {
        return visitorPlayerId;
    }

    public Integer getLocalPlayerScore() {
        return localPlayerScore;
    }

    public Integer getVisitorPlayerScore() {
        return visitorPlayerScore;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public Integer getNextMatchId() {
        return nextMatchId;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Match other = (Match) obj;
        if (matchId == null) {
            if (other.matchId != null)
                return false;
        } else if (!matchId.equals(other.matchId))
            return false;
        if (tournamentId == null) {
            if (other.tournamentId != null)
                return false;
        } else if (!tournamentId.equals(other.tournamentId))
            return false;
        return true;
    }
}
