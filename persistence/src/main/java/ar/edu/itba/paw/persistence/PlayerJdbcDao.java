package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
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
public class PlayerJdbcDao implements PlayerDao {

    private static Integer DOWN_OFFSET = -1, UP_OFFSET = 1;

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert playerjdbcInsert;

    private final SimpleJdbcInsert participatesInjdbcInsert;

    private final static RowMapper<Player> ROW_MAPPER = (rs, rowNum) -> new Player(rs.getString("name"), rs.getLong("player_id"), rs.getLong("user_id"));

    private final static RowMapper<Player> ROW_MAPPER_USERNAME = (rs, rowNum) -> new Player(rs.getString("name"), rs.getLong("player_id"), rs.getLong("user_id"), rs.getString("user_name"));

    @Autowired
    public PlayerJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        playerjdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("player")
                .usingGeneratedKeyColumns("player_id");
        participatesInjdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("participates_in")
                .usingColumns("player_id", "tournament_id", "seed", "standing");
    }

    @Override
    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM player WHERE player_id = ?", id);
    }

    @Override
    public boolean removeToTournament(long tournamentId, long playerId) {
        String status = jdbcTemplate.queryForObject("SELECT status FROM tournament WHERE tournament_id = ?",String.class, tournamentId);
        if(!status.equals(Tournament.Status.NEW.toString())) {
            return false;
        }
        List<Integer> list = jdbcTemplate.queryForList("(SELECT seed FROM participates_in WHERE tournament_id = ? AND seed > (SELECT seed FROM participates_in WHERE tournament_id = ? AND player_id = ?)) ORDER BY seed ASC",
                Integer.class, tournamentId, tournamentId, playerId);
        if(list != null) {
            changePlayersSeed(list, tournamentId, DOWN_OFFSET);
        }
        jdbcTemplate.update("DELETE FROM participates_in WHERE  tournament_id = ? AND player_id = ?", tournamentId, playerId);
        return true;
    }

    @Override
    public boolean changeSeedToTournament(long tournamentId, int playerOldSeed, int playerNewSeed) {
        String status = jdbcTemplate.queryForObject("SELECT status FROM tournament WHERE tournament_id = ?",String.class, tournamentId);
        if(!status.equals(Tournament.Status.NEW.toString())) {
            return false;
        }
        Integer playerId = jdbcTemplate.queryForObject("SELECT player_id FROM participates_in WHERE tournament_id = ? AND seed = ?", Integer.class, tournamentId, playerOldSeed);
        List<Integer> list = null;
        if(playerOldSeed < playerNewSeed) {
            list = jdbcTemplate.queryForList("SELECT seed FROM participates_in WHERE tournament_id = ? AND seed > ? AND seed <= ? ORDER BY seed ASC",
                    Integer.class, tournamentId, playerOldSeed, playerNewSeed);
            if(list != null) {
                changePlayersSeed(list, tournamentId, DOWN_OFFSET);
            }
        } else {
            list = jdbcTemplate.queryForList("SELECT seed FROM participates_in WHERE tournament_id = ? AND seed >= ? AND seed < ? ORDER BY seed DESC",
                    Integer.class, tournamentId, playerNewSeed, playerOldSeed);
            if(list != null) {
                changePlayersSeed(list, tournamentId, UP_OFFSET);
            }
        }
        jdbcTemplate.update("UPDATE participates_in SET seed = ? WHERE tournament_id = ? AND player_id = ?", playerNewSeed, tournamentId, playerId);
        return true;
    }

    private void changePlayersSeed(List<Integer> list, long tournamentId, int offset) {
        for(Integer seed : list) {
            jdbcTemplate.update("UPDATE participates_in SET seed = ? WHERE tournament_id = ? AND seed = ?", (seed+offset),tournamentId, seed);
        }
    }

    @Override
    public Player findById(long id) {
        final List<Player> list = jdbcTemplate.query("SELECT * FROM player WHERE player_id = ?",
                ROW_MAPPER, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private final static RowMapper<Long> LONG_MAPPER = (rs, rowNum) -> (rs.getLong("player_id"));

    @Override
    public long findBySeed(int seed, long tournamentId) {


        final List<Long> list = jdbcTemplate.query("SELECT * FROM participates_in WHERE seed = ? AND tournament_id = ?", LONG_MAPPER, seed, tournamentId);

        if (list.isEmpty()) {
            return PlayerDao.EMPTY;
        }
        return list.get(0);
    }

    @Override
    public Player create(String name) {
        if(name.length() > PlayerDao.NAME_LENGTH) {
            return null;
        }
        final Map<String, Object> args = new HashMap<>();

        args.put("name", name);
        final Number playerId = playerjdbcInsert.executeAndReturnKey(args);
        return new Player(name, playerId.longValue());
    }

    @Override
    public boolean addToTournament(long playerId, long tournamentId, int seed) {

        String status = jdbcTemplate.queryForObject("SELECT status FROM tournament WHERE tournament_id = ?",String.class, tournamentId);
        if(!status.equals(Tournament.Status.NEW.toString())) {
            return false;
        }
        final Map<String, Object> args = new HashMap<>();
        args.put("player_id", playerId);
        args.put("tournament_id", tournamentId);
        args.put("seed", seed);
        args.put("standing", PlayerDao.EMPTY_STANDING);

        int numberOfRowsInserted = participatesInjdbcInsert.execute(args);

        return numberOfRowsInserted == PlayerDao.SUCCESSFUL_INSERT;
    }

    @Override
    public void addToTournament(long playerId, long tournamentId) {

        int seed = getNextSeed(tournamentId);

        final Map<String, Object> args = new HashMap<>();
        args.put("player_id", playerId);
        args.put("tournament_id", tournamentId);
        args.put("seed", seed);

        participatesInjdbcInsert.execute(args);
    }

    private int getNextSeed(long tournamentId) {
        return 1 + jdbcTemplate.queryForObject("SELECT COALESCE(max(seed), 0) FROM participates_in WHERE tournament_id = ?", Integer.class, tournamentId);
    }

    @Override
    public List<Player> getTournamentPlayers(long tournamentId) {
        return jdbcTemplate.query("SELECT player.name, player_id, users.user_id, users.user_name FROM (player NATURAL JOIN participates_in)" +
                "LEFT JOIN users ON player.user_id = users.user_id WHERE tournament_id = ? ORDER BY seed", ROW_MAPPER_USERNAME, tournamentId);
    }

    @Override
    public void setDefaultStanding(int defaultStanding, long tournamentId) {
        jdbcTemplate.update("UPDATE participates_in SET standing = ? WHERE tournament_id = ?", defaultStanding, tournamentId);
    }

    @Override
    public Player create(String name, long userId) {
        if(name.length() > PlayerDao.NAME_LENGTH) {
            return null;
        }
        final Map<String, Object> args = new HashMap<>();

        args.put("name", name);
        args.put("user_id", userId);
        final Number playerId = playerjdbcInsert.executeAndReturnKey(args);
        return new Player(name, playerId.longValue());
    }
}