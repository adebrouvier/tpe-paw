package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.stereotype.Repository;

//TODO Implement tournament persistence
@Repository
public class TournamentJdbcDao implements TournamentDao {

    @Override
    public Tournament findById(long id) {
        return null;
    }

    @Override
    public Tournament create(String name, String players) {
        return null;
    }
}
