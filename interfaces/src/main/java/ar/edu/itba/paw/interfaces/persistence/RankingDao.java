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
    int SEVENTH = 7;
    double FIRST_SCORE = 0.4;
    double SECOND_SCORE = 0.2;
    double THIRD_SCORE = 0.15;
    double FOURTH_SCORE = 0.1;
    double FIFTH_SCORE = 0.1;
    double SEVENTH_SCORE = 0.25;
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
     * @param userId id of the user that created the ranking
     * @return instance of new ranking
     */
     Ranking create(final String name, final Map<Tournament, Integer> tournaments, String game, long userId);

    /**
     * Finds any Ranking that matches the
     * specified name
     * @param term name of the Rankings
     * @return a list of instances of the
     * matched Rankings
     */
     List<Ranking> findByName(String term);

    //TODO: add javadoc
    List<String> findRankingNames(String query);

    /**
     * Deletes a Tournament from a Ranking both
     * specified by id
     * @param rankingId id of the Ranking
     * @param tournamentId id of the Tournament
     */
    void delete(final long rankingId, final long tournamentId);

    /**
     * Add Tournaments to a specified Ranking
     * @param rankingId id of the Ranking
     * @param tournaments Map of Tournament
     *                    and the points each
     *                    one awards
     * @return instance of the modified Ranking
     */
    Ranking addTournaments(final long rankingId, Map<Tournament, Integer> tournaments);

    /**
     *
     * Returns a list of the featured Rankings
     * @param featured amount of Ranking to be featured
     * @return list of the featured Rankings
     */
    List<Ranking> findFeaturedRankings(int featured);
}
