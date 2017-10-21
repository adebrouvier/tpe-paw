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
    public Ranking findById(final long rankingId);

    /**
     *Creates a ranking with specified name and
     * a list of tournaments with its corresponding points
     * @param name of the ranking
     * @param tournaments taken into account
     * @return instance of new ranking
     */
    public Ranking create(final String name, final Map<Tournament,Integer> tournaments);

    /**
     * Deletes a tournament from a ranking that already
     * existed.
     * @param rankingId id of the ranking
     * @param tournamentId id of the tournament
     */
    public void delete(final long rankingId, final long tournamentId);

    /**
     * Searches for a {@link Ranking}
     * @param term name of part of the name
     * @return a list of matching rankings
     */
    public List<Ranking> findByName(String term);

    List<String> findRankingNames(String query);
}
