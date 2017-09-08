package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//TODO implement persistence
@Repository
public class PlayerJdbcDao implements PlayerDao{
    @Override
    public Player findById(long id) {
        return null;
    }

    @Override
    public Player create(String name) {
        return null;
    }

    @Autowired
    private TournamentService ts;

    @Override
    public boolean addToTournament(long playerId, long tournamentId) {




        return false;
    }
}
