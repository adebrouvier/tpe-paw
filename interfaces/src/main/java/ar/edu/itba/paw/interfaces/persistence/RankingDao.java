package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;
import java.util.Map;

public interface RankingDao {

    int FIRST = 1;
    int SECOND = 2;
    int THIRD = 3;
    int FOURTH = 4;
    int FIFTH = 5;
    double FIRST_SCORE = 0.4;
    double SECOND_SCORE = 0.3;
    double THIRD_SCORE = 0.2;
    double FOURTH_SCORE = 0.05;
    double FIFTH_SCORE = 0.025;
    int NO_SCORE = 0;


    /**
     * Finds the ranking with the specified id
     * @param rankingId id of the tournament
     * @return instance of the Ranking
     */
     Ranking findById(final long rankingId);

    /**
     *Creates a ranking with specified name and
     * a list of tournaments with its corresponding points
     * @param name of the ranking
     * @param tournaments taken into account
     * @param game that the tournaments will feature
     * @return instance of new ranking
     */
     Ranking create(final String name, final Map<Tournament, Integer> tournaments, String game);

     List<Ranking> findByName(String term);

    List<String> findRankingNames(String query);

     void delete(final long rankingId, final long tournamentId);

    Ranking addTournaments(final long rankingId, Map<Tournament, Integer> tournaments);

    List<Ranking> findFeaturedRankings(int featured);
}
