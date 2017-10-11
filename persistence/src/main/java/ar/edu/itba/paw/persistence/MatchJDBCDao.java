package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.MatchDao;
import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
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
public class MatchJDBCDao implements MatchDao {

    private JdbcTemplate jdbcTemplate;

    private static Integer EMPTY = 0;

    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Match> ROW_MAPPER = (rs, rowNum) ->
            new Match(rs.getLong("home_player_id"), rs.getLong("away_player_id"), rs.getInt("home_player_score"), rs.getInt("away_player_score"), rs.getLong("match_id"), rs.getInt("next_match_id"), rs.getBoolean("next_match_home"), rs.getLong("tournament_id"));

    @Autowired
    public MatchJDBCDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .usingColumns("match_id", "tournament_id", "next_match_id", "home_player_id", "away_player_id", "home_player_score", "away_player_score", "next_match_home")
                .withTableName("match");
    }

    @Override
    public Match createEmpty(final int matchId, final int nextMatchId, final boolean isNextMatchHome, final long tournamentId) {
        final Map<String, Object> args = new HashMap<>();
        args.put("match_id", matchId);
        args.put("tournament_id", tournamentId);

        if (nextMatchId != 0)
            args.put("next_match_id", nextMatchId);
        else
            args.put("next_match_id", null);

        args.put("next_match_home", isNextMatchHome);
        jdbcInsert.execute(args);
        return new Match(matchId, nextMatchId, isNextMatchHome, tournamentId);
    }

    @Override
    public Match create(int matchId, int nextMatchId, boolean isNextMatchHome, long tournamentId, long homePlayerId, long awayPlayerId) {

        int homeScore = 0;

        final Map<String, Object> args = new HashMap<>();
        args.put("match_id", matchId);
        args.put("tournament_id", tournamentId);
        args.put("home_player_id", homePlayerId);
        args.put("away_player_id", awayPlayerId);
        args.put("next_match_home", isNextMatchHome);

        if (nextMatchId != 0){
            args.put("next_match_id", nextMatchId);
        }else{
            args.put("next_match_id", null);
        }

        if (awayPlayerId == -1) { /*Checks if there is a BYE*/
            homeScore = MatchService.BYE_WIN_SCORE;
            args.put("home_player_score", homeScore);
        }

        updateNextMatch(tournamentId,nextMatchId,homeScore,0,homePlayerId,awayPlayerId,isNextMatchHome);

        jdbcInsert.execute(args);
        return new Match(homePlayerId, awayPlayerId, homeScore,0, matchId, nextMatchId, isNextMatchHome, tournamentId);
    }

    @Autowired
    private PlayerDao playerDao;

    @Override
    public Match findById(final int matchId, final long tournamentId) {
        List<Match> list = jdbcTemplate.query("SELECT * FROM match WHERE match_id = ? and tournament_id = ?", ROW_MAPPER, matchId, tournamentId);

        Match m = list.get(0);

        if (m != null) {
            Player homePlayer = playerDao.findById(m.getHomePlayerId());
            Player awayPlayer = playerDao.findById(m.getAwayPlayerId());
            m.setHomePlayer(homePlayer);
            m.setAwayPlayer(awayPlayer);
        }
        return m;
    }

    @Override
    public Match updateScore(long tournamentId, int matchId, int homeScore, int awayScore) {

        //TODO: check ties and defined matches

        Match match = findById(matchId, tournamentId);

        if (match == null){
            return null;
        }

        if(match.getHomePlayerId() == EMPTY || match.getAwayPlayerId() == EMPTY) {
            return null;
        }

        jdbcTemplate.update("UPDATE match SET home_player_score = ?, away_player_score = ? WHERE match_id = ? AND tournament_id = ?", homeScore, awayScore, matchId, tournamentId);

        updateNextMatch(tournamentId,match.getNextMatchId(),homeScore,awayScore,match.getHomePlayerId(),match.getAwayPlayerId(),match.isNextMatchHome());

        return findById(matchId, tournamentId);
    }

    private void updateNextMatch(long tournamentId, long nextMatchId, int homeScore, int awayScore, long homePlayerId, long awayPlayerId, boolean nextMatchHome){


        if(nextMatchId == 0) {
        //    jdbcTemplate.update("UPDATE tournament SET is_finished = ? WHERE tournament_id = ?", true, tournamentId);
            return;
        }

        Match match = findById((int)nextMatchId, tournamentId);

        if(match != null) {
            if(nextMatchHome) {
                if(match.getHomePlayerId() != EMPTY) {
                    if(homeScore > awayScore) {
                        if(match.getHomePlayerId() != homePlayerId) {
                            jdbcTemplate.update("UPDATE match SET home_player_id = ?, home_player_score = ?, away_player_score = ?  WHERE match_id = ? AND tournament_id = ?", homePlayerId, 0, 0, nextMatchId, tournamentId);
                            updateRecursive(tournamentId, match.getNextMatchId(), match.isNextMatchHome());
                            return;
                        }
                    }
                    if(homeScore < awayScore) {
                        if(match.getHomePlayerId() != awayPlayerId) {
                            jdbcTemplate.update("UPDATE match SET home_player_id = ?, home_player_score = ?, away_player_score = ?  WHERE match_id = ? AND tournament_id = ?", awayPlayerId, 0, 0, nextMatchId, tournamentId);
                            updateRecursive(tournamentId, match.getNextMatchId(), match.isNextMatchHome());
                            return;
                        }
                    }
                }

            } else {
                if(match.getAwayPlayerId() != EMPTY) {
                    if(homeScore > awayScore) {
                        if(match.getAwayPlayerId() != homePlayerId) {
                            jdbcTemplate.update("UPDATE match SET away_player_id = ?, home_player_score = ?, away_player_score = ?  WHERE match_id = ? AND tournament_id = ?", homePlayerId, 0, 0, nextMatchId, tournamentId);
                            updateRecursive(tournamentId, match.getNextMatchId(), match.isNextMatchHome());
                            return;
                        }
                    }
                    if(homeScore < awayScore) {
                        if(match.getAwayPlayerId() != awayPlayerId) {
                            jdbcTemplate.update("UPDATE match SET away_player_id = ?, home_player_score = ?, away_player_score = ?  WHERE match_id = ? AND tournament_id = ?", awayPlayerId, 0, 0, nextMatchId, tournamentId);
                            updateRecursive(tournamentId, match.getNextMatchId(), match.isNextMatchHome());
                            return;
                        }
                    }
                }

            }
        }

        if (homeScore == awayScore){
            return;
        }

        long winnerId = 0;


        if (homeScore > awayScore) {
            winnerId = homePlayerId;
        } else if (awayScore > homeScore){
            winnerId = awayPlayerId;
        }
        if (nextMatchHome) {
            jdbcTemplate.update("UPDATE match SET home_player_id = ? WHERE match_id = ? AND tournament_id = ?", winnerId, nextMatchId, tournamentId);
        } else {
            jdbcTemplate.update("UPDATE match SET away_player_id = ? WHERE match_id = ? AND tournament_id = ?", winnerId, nextMatchId, tournamentId);
        }

    }

    private void updateRecursive(long tournamentId, long matchId, boolean nextMatchHome) {

        if(matchId == 0) {
            return;
        }

        Match match = findById((int)matchId, tournamentId);
        if(match != null) {
            if(match.getId() != 0) {
                if(nextMatchHome) {
                    if(match.getHomePlayerId() != 0) {
                        jdbcTemplate.update("UPDATE match SET home_player_id = ?, home_player_score = ?, away_player_score = ? WHERE match_id = ? AND tournament_id = ?", null, 0, 0, matchId, tournamentId);
                        updateRecursive(tournamentId, match.getNextMatchId(), match.isNextMatchHome());
                    }
                } else {
                    if(match.getAwayPlayerId() != 0) {
                        jdbcTemplate.update("UPDATE match SET away_player_id = ?, home_player_score = ?, away_player_score = ? WHERE match_id = ? AND tournament_id = ?", null, 0, 0, matchId, tournamentId);
                        updateRecursive(tournamentId, match.getNextMatchId(), match.isNextMatchHome());
                    }
                }
            }

        }
    }

    @Override
    public List<Match> getTournamentMatches(long tournamentId) {
        List<Match> matches = jdbcTemplate.query("SELECT * FROM match" +
                " WHERE tournament_id = ? ORDER BY match_id ASC", ROW_MAPPER, tournamentId);

        //TODO ask if is okay to do this
        for (Match m : matches) {
            Player homePlayer = playerDao.findById(m.getHomePlayerId());
            Player awayPlayer = playerDao.findById(m.getAwayPlayerId());
            m.setHomePlayer(homePlayer);
            m.setAwayPlayer(awayPlayer);
        }

        return matches;
    }


}
