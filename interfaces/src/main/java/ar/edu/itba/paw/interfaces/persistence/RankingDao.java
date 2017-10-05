package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;

import java.util.List;

public interface RankingDao {
    public Ranking findById(final long rankingId);
    public Ranking create(final int pointsAwarded, final String name, final List<Tournament> tournaments);
}
