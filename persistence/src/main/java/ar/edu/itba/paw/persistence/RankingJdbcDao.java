package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.persistence.RankingDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RankingJdbcDao implements RankingDao {

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    private final SimpleJdbcInsert tournamentToRankingInsert;

    private final SimpleJdbcInsert playerToRankingInsert;

    private final static RowMapper<Ranking> RANKING_MAPPER = (rs, rowNum) -> new Ranking(rs.getInt("ranking_id"), rs.getString("name"));

    private final static RowMapper<PlayerScores> PLAYER_RANKING_MAPPER = (rs, rowNum) -> new PlayerScores(rs.getString("name"),rs.getInt("points"));

    private final static RowMapper<TournamentPoints> TOURNAMENT_MAPPER = (rs, rowNum) -> new TournamentPoints(rs.getInt("tournament_id"),rs.getInt("awarded_points"));

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
                .usingColumns("ranking_id", "tournament_id","awarded_points");
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
        Map<Tournament,Integer> rankingTournaments = getRankingTournaments(rankingId);
        r.setTournaments(rankingTournaments);
        r.setPlayers(rankingPlayers);
        return r;
    }

    @Override
    public Ranking create(String name, Map<Tournament,Integer> tournaments) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);

        final Number rankingId = jdbcInsert.executeAndReturnKey(args);

        addTournamentToRanking(rankingId.longValue(), tournaments);
        addPlayersToRanking(rankingId.longValue(), tournaments);

        return new Ranking(rankingId.longValue(), name);
    }

    public List<PlayerScores> getRankingPlayers(long rankingId){
        List<PlayerScores> players;
        players = jdbcTemplate.query("SELECT name, points FROM ranking_players WHERE ranking_id = ?",PLAYER_RANKING_MAPPER,rankingId);
        return players;

    }
    /*Complexity?*/
    public Map<Tournament,Integer> getRankingTournaments(long rankingId){
        Map<Tournament,Integer> tournaments = new HashMap<>();
        List<TournamentPoints> tournamentsPoints = jdbcTemplate.query("SELECT tournament_id, awarded_points FROM ranking_tournaments WHERE ranking_id = ?",TOURNAMENT_MAPPER,rankingId);
        for(TournamentPoints tPoints: tournamentsPoints){
            tournaments.put(tournamentDao.findById(tPoints.getTournamentId()),tPoints.getAwardedPoints());
        }
        return tournaments;

    }

    private void addTournamentToRanking(long rankingId, Map<Tournament,Integer> tournaments) {
        final Map<String, Object> args = new HashMap<>();
        for (Tournament tournament : tournaments.keySet()) {
            args.clear();
            args.put("ranking_id", rankingId);
            args.put("tournament_id",tournament.getId());
            args.put("awarded_points",tournaments.get(tournament));
            tournamentToRankingInsert.execute(args);
        }

    }

    private void addPlayersToRanking(long rankingId, Map<Tournament,Integer> tournaments) {
        final Map<String, Object> args = new HashMap<>();
        Integer existingPlayerScore;
        for (Tournament tournament : tournaments.keySet()) {
            for (Player player : tournament.getPlayers()) {
                if(player.getId() != -1){
                  /*Se podria en una query sacar el player y no tener que hacer otra query para sacar los puntos*/
                    Integer playerExists = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ranking_players WHERE ranking_id = ? AND name = ?", Integer.class, rankingId, player.getName());
                    if (playerExists > 0) {
                        existingPlayerScore = jdbcTemplate.queryForObject("SELECT points FROM ranking_players WHERE name = ? AND ranking_id = ?", Integer.class,player.getName(), rankingId);
                    /*Add the logic of awarded points*/
                        jdbcTemplate.update("UPDATE ranking_players SET points = ? WHERE name = ? AND ranking_id = ? ", existingPlayerScore + tournaments.get(tournament) ,player.getName(), rankingId);
                    } else {
                        args.clear();
                        args.put("ranking_id", rankingId);
                        args.put("name",player.getName());
                        args.put("points", tournaments.get(tournament));
                        playerToRankingInsert.execute(args);
                    }
                }

            }
        }
    }
}
