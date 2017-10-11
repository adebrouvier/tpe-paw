package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TournamentJdbcDao implements TournamentDao {

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Tournament> ROW_MAPPER = (rs, rowNum) -> new Tournament(rs.getString("name"), rs.getInt("tournament_id"), rs.getBoolean("is_finished"), rs.getInt("tier"));

    @Autowired
    public TournamentJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("tournament")
                .usingGeneratedKeyColumns("tournament_id");
    }

    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private MatchDao matchDao;

    @Override
    public Tournament findById(long id) {
        final List<Tournament> list = jdbcTemplate.query("SELECT * FROM tournament WHERE tournament_id = ?",
                ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }

        Tournament t  = list.get(0);

        final List<Player> players  = playerDao.getTournamentPlayers(id);
        final List<Match> matches = matchDao.getTournamentMatches(id);

        t.addPlayer(players);
        t.addMatch(matches);

        return t;
    }

    @Override
    public Tournament create(String name) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("is_finished", false);
        args.put("tier", 1);
        final Number tournamentId = jdbcInsert.executeAndReturnKey(args);
        return new Tournament(name,tournamentId.longValue());
    }


    public Tournament create(String name, int tier) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("is_finished", false);
        args.put("tier", tier);
        final Number tournamentId = jdbcInsert.executeAndReturnKey(args);
        return new Tournament(name,tournamentId.longValue());
    }


    @Override
    public List<Tournament> findFeaturedTournaments() {

        int featured = 10;

        final List<Tournament> list = jdbcTemplate.query("SELECT * FROM tournament ORDER BY tournament_id DESC LIMIT ?", ROW_MAPPER, featured);

        if (list.isEmpty()) {
            return null;
        }

        for (Tournament t : list) {
            Integer numberOfMatches = jdbcTemplate.queryForObject("SELECT count(*) FROM match WHERE tournament_id = ?", Integer.class, t.getId());
            Integer numberOfPlayers = jdbcTemplate.queryForObject("SELECT count(*) FROM participates_in WHERE tournament_id = ?", Integer.class, t.getId());
            t.setSize(numberOfPlayers);
            t.setNumberOfMatches(numberOfMatches);
        }

        return list;
    }

    public void endTournament(long tournamentId) {
        jdbcTemplate.update("UPDATE tournament SET is_finished = ? WHERE tournament_id = ?", true,tournamentId);
    }

}
