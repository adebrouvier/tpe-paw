package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Standing;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;

public interface TournamentDao {

    public Tournament findById (long id);

    public Tournament create(String name, String game);

    public Tournament create(String name, String game,int tier);

    public List<Tournament> findFeaturedTournaments();

    public void endTournament(long tournamentId);

    public List<Standing> getStandings(long tournamentId);

    public List<String> findTournamentNames();

    public List<Tournament> findByName(String name);
}
