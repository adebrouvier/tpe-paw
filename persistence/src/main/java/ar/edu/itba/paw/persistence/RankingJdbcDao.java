package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.persistence.RankingDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.PlayerScores;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RankingJdbcDao implements RankingDao {

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    private final SimpleJdbcInsert tournamentToRankingInsert;

    private final SimpleJdbcInsert playerToRankingInsert;

    private final static RowMapper<Ranking> RANKING_MAPPER = (rs, rowNum) -> new Ranking(rs.getInt("ranking_id"), rs.getInt("awarded_points"), rs.getString("name"));

    private final static RowMapper<PlayerScores> PLAYER_RANKING_MAPPER = (rs, rowNum) -> new PlayerScores(rs.getInt("player_id"),rs.getInt("points"));


    @Autowired
    private PlayerDao playerDao;

    @Autowired
    private TournamentDao tournamentDao;

    @Autowired
    public RankingJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ranking")
                .usingGeneratedKeyColumns("ranking_id");
        tournamentToRankingInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ranking_tournaments")
                .usingColumns("ranking_id", "tournament_id");
        playerToRankingInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ranking_players")
                .usingColumns("ranking_id","name","points");
    }

    @Override
    public Ranking findById(long rankingId) {
        final List<Ranking> list = jdbcTemplate.query("SELECT * FROM ranking WHERE ranking_id = ?",
                RANKING_MAPPER, rankingId);
        if (list.isEmpty()) {
            return null;
        }

        Ranking r  = list.get(0);
        /*Check if this is really useful, and how players are obtained*/
        List<PlayerScores> rankingPlayers = getRankingPlayers(rankingId);
        List<Tournament> rankingTournaments = getRankingTournaments(rankingId);
        r.setTournaments(rankingTournaments);
        r.setPlayers(rankingPlayers);
        return r;
    }

    @Override
    public Ranking create(int pointsAwarded, String name, List<Tournament> tournaments) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("awarded_points", pointsAwarded);

        final Number rankingId = jdbcInsert.executeAndReturnKey(args);

        addTournamentToRanking(rankingId.longValue(), tournaments);
        addPlayersToRanking(rankingId.longValue(), tournaments);

        return new Ranking(rankingId.longValue(), pointsAwarded, name);
    }

    public List<PlayerScores> getRankingPlayers(long rankingId){
        List<PlayerScores> players;
        players = jdbcTemplate.query("SELECT player_id, points FROM ranking_players WHERE ranking_id = ?",PLAYER_RANKING_MAPPER,rankingId);
        return players;

    }

    public List<Tournament> getRankingTournaments(long rankingId){
        List<Tournament> tournaments = new ArrayList<>();
        List<Long> tournamentsId = jdbcTemplate.queryForList("SELECT tournament_id FROM ranking_tournaments WHERE ranking_id = ?",Long.class,rankingId);
        for(Long id:tournamentsId){
            tournaments.add(tournamentDao.findById(id));
        }
        return tournaments;

    }

    private void addTournamentToRanking(long rankingId, List<Tournament> tournaments) {
        final Map<String, Object> args = new HashMap<>();
        for (Tournament tournament : tournaments) {
            args.put("ranking_id", rankingId);
            args.put("tournament_id",tournament.getId());
            tournamentToRankingInsert.executeAndReturnKey(args);
        }

    }

    private void addPlayersToRanking(long rankingId, List<Tournament> tournaments) {
        final Map<String, Object> args = new HashMap<>();
        Integer existingPlayerScore;
        for (Tournament tournament : tournaments) {
            for (Player player : tournament.getPlayers()) {
                /*Se podria en una query sacar el player y no tener que hacer otra query para sacar los puntos*/
                Integer playerExists = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ranking_players WHERE ranking_id = ? name = ?)", Integer.class, rankingId, player.getName());
                if (playerExists > 0) {
                    existingPlayerScore = jdbcTemplate.queryForObject("SELECT points FROM ranking_players WHERE name = ?", Integer.class,player.getName());
                    jdbcTemplate.update("UPDATE ranking_players SET points = ? WHERE player_id = ? ", existingPlayerScore + player.getStanding() ,player.getId());
                } else {
                    args.put("ranking_id", rankingId);
                    args.put("name",player.getName());
                    args.put("points", player.getStanding());
                    playerToRankingInsert.executeAndReturnKey(args);
                }
            }
        }
    }
}
