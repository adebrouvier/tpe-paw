package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Standing;
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

    private final static RowMapper<Tournament> ROW_MAPPER = (rs, rowNum) -> new Tournament(rs.getString("name"), rs.getInt("tournament_id"));

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
            Integer numberOfMatches = jdbcTemplate.queryForObject("SELECT count(*) FROM match WHERE tournament_id = ? AND coalesce(away_player_id, 0) != ?", Integer.class, t.getId(), TournamentService.BYE_ID);
            Integer numberOfPlayers = jdbcTemplate.queryForObject("SELECT count(*) FROM participates_in WHERE tournament_id = ? AND player_id != ?", Integer.class, t.getId(), TournamentService.BYE_ID);
            t.setSize(numberOfPlayers);
            t.setNumberOfMatches(numberOfMatches);
        }

        return list;
    }

    private final static RowMapper<Standing> STANDING_ROW_MAPPER = (rs, rowNum) -> new Standing(rs.getString("name"), rs.getInt("standing"));

    @Override
    public List<Standing> getStandings(long tournamentId) {

        List<Standing> standingList = jdbcTemplate.query("SELECT player.name AS name, participates_in.standing as standing  FROM player NATURAL JOIN participates_in WHERE tournament_id = ? AND player_id != ? ORDER BY standing ASC", STANDING_ROW_MAPPER, tournamentId, TournamentService.BYE_ID);

        return standingList;
    }

    @Override
    public List<String> findTournamentNames() {
        return jdbcTemplate.queryForList("SELECT name FROM tournament", String.class);
    }

    @Override
    public List<Tournament> findByName(String name) {

        StringBuilder sb = new StringBuilder(name);
        sb.insert(0,"%");
        sb.append("%");
        final List<Tournament> list = jdbcTemplate.query("SELECT * FROM tournament WHERE name LIKE ?",
                ROW_MAPPER, sb);
        if (list.isEmpty()) {
            return null;
        }

        return list;
    }
}
