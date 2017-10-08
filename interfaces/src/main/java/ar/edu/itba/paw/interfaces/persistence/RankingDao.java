package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;
import java.util.Map;

public interface RankingDao {
    public Ranking findById(final long rankingId);
    public Ranking create(final String name, final Map<Tournament, Integer> tournaments);
}
