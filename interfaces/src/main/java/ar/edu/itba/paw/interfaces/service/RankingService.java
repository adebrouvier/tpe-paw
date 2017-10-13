package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;

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
}
