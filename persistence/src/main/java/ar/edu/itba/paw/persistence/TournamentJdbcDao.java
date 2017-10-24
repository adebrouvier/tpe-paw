package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.*;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.*;

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

    private final static RowMapper<Tournament> ROW_MAPPER = (rs, rowNum) -> new Tournament(rs.getString("name"), rs.getLong("tournament_id"), rs.getLong("game_id"), Tournament.Status.valueOf(rs.getString("status")), rs.getLong("user_id"));

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

    @Autowired
    private GameDao gameDao;

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

        //TODO: el model deberia hacer eso.
        Integer numberOfMatches = getNumberOfMatches(t.getId());
        Integer numberOfPlayers = getNumberOfPlayers(t.getId());
        t.setSize(numberOfPlayers);
        t.setNumberOfMatches(numberOfMatches);

        return t;
    }

    @Override
    public Tournament create(String name, long gameId, long userId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("user_id", userId);
        args.put("status", Tournament.Status.NEW);
        Game game = gameDao.findById(gameId);
        if(game == null) {
            return null;
        }
        args.put("game_id", gameId);
        final Number tournamentId = jdbcInsert.executeAndReturnKey(args);
        return new Tournament(name, tournamentId.longValue(), gameId, Tournament.Status.NEW, userId);
    }

    @Override
    public List<Tournament> findFeaturedTournaments(int featured) {

        final List<Tournament> list = jdbcTemplate.query("SELECT * FROM tournament ORDER BY tournament_id DESC LIMIT ?", ROW_MAPPER, featured);

        if (list.isEmpty()) {
            return null;
        }

        for (Tournament t : list) {
            Integer numberOfMatches = getNumberOfMatches(t.getId());
            Integer numberOfPlayers = getNumberOfPlayers(t.getId());
            t.setSize(numberOfPlayers);
            t.setNumberOfMatches(numberOfMatches);
            t.setGame(gameDao.findById(t.getGameId()));
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
    public List<String> findTournamentNames(String query) {
        StringBuilder sb = new StringBuilder(query.toLowerCase());
        sb.insert(0,"%");
        sb.append("%");
        return jdbcTemplate.queryForList("SELECT name FROM tournament WHERE lower(name) LIKE ?",  String.class, sb.toString());
    }

    @Override
    public List<Tournament> findByName(String name) {

        StringBuilder sb = new StringBuilder(name.toLowerCase());
        sb.insert(0,"%");
        sb.append("%");
        final List<Tournament> list = jdbcTemplate.query("SELECT * FROM tournament WHERE lower(name) LIKE ?",
                ROW_MAPPER, sb);
        if (list.isEmpty()) {
            return null;
        }

        for (Tournament t : list){
            t.setNumberOfMatches(getNumberOfMatches(t.getId()));
            t.setSize(getNumberOfPlayers(t.getId()));
            t.setGame(gameDao.findById(t.getGameId()));
        }

        return list;
    }

    @Override
    public Tournament getByName(String name) {

        final List<Tournament> list = jdbcTemplate.query("SELECT * FROM tournament WHERE name = ?",
                ROW_MAPPER, name);
        if (list.isEmpty()) {
            return null;
        }
        list.get(0).setPlayers(playerDao.getTournamentPlayers(list.get(0).getId()));
        return list.get(0);
    }

    @Override
    public void setStatus(long tournamentId, Tournament.Status status) {
        jdbcTemplate.update("UPDATE tournament SET status = ? WHERE tournament_id = ?", status.toString(), tournamentId);
    }

    private int getNumberOfMatches(long tournamentId){
        return jdbcTemplate.queryForObject("SELECT count(*) FROM match WHERE tournament_id = ? AND coalesce(away_player_id, 0) != ?", Integer.class, tournamentId, TournamentService.BYE_ID);
    }

    private int getNumberOfPlayers(long tournamentId){
        return jdbcTemplate.queryForObject("SELECT count(*) FROM participates_in WHERE tournament_id = ? AND player_id != ?", Integer.class, tournamentId, TournamentService.BYE_ID);
    }

}
