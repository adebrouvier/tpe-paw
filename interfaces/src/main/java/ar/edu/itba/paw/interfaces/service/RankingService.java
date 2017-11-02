package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;
import java.util.Map;

public interface RankingService {


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
    Ranking create(final String name, final Map<Tournament, Integer> tournaments, String game, long userId);

    /**
     * Deletes a tournament from a ranking that already
     * existed.
     * @param rankingId id of the ranking
     * @param tournamentId id of the tournament
     */
    void delete(final long rankingId, final long tournamentId);

    /**
     * Searches for a {@link Ranking}
     * @param term name of part of the name
     * @return a list of matching rankings
     */
    List<Ranking> findByName(String term);

    /**
     * Searchs by a partially completed name for a Ranking that
     * matches
     * @param query partial name
     * @return list of Rankings that matched
     */
    List<String> findRankingNames(String query);

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
