package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.*;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Game;
import ar.edu.itba.paw.model.Match;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Standing;
import ar.edu.itba.paw.model.Tournament;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TournamentJdbcDao implements TournamentDao {

    private JdbcTemplate jdbcTemplate;

    private final SimpleJdbcInsert jdbcInsert;

    private final static RowMapper<Tournament> ROW_MAPPER = (rs, rowNum) -> new Tournament(rs.getString("name"), rs.getLong("tournament_id"), rs.getBoolean("is_finished"),  rs.getInt("tier") ,rs.getLong("game_id"));

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

    @Autowired
    private GameUrlImageDao gameUrlImageDao;

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
    public Tournament create(String name, String gameName) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("is_finished", false);
        args.put("tier", 1);
        Game game = gameDao.findByName(gameName);
        if(game == null) {
            game = gameDao.create(gameName, true);
            StringBuilder url = new StringBuilder("https://player.me/api/v1/search?sort=popular&order=desc&_limit=1&q=");
            String[] gameWords = gameName.split(" ");
            int size = gameWords.length;
            int i = 0;
            for( ; i < size-1 ; i++) {
                url = url.append(gameWords[i] + "%20");
            }
            url = url.append(gameWords[i]);

            try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
                HttpGet request = new HttpGet(url.toString());
                request.addHeader("content-type", "application/json");
                HttpResponse result = httpClient.execute(request);

                String json = EntityUtils.toString(result.getEntity(), "UTF-8");
                JSONObject obj = new JSONObject(json);

                if(obj != null) {
                    String title = obj.getJSONArray("results").getJSONObject(0).getString("title");
                    if(title != null) {
                        if(title.equals(gameName)) {
                            String cover = obj.getJSONArray("results").getJSONObject(0).getJSONObject("cover").getString("original");
                            if(cover != null) {
                                StringBuilder urlImage = new StringBuilder("https:");
                                String[] arg =cover.split("\\\\");
                                for(String part : arg) {
                                    urlImage = urlImage.append(part);
                                }
                                gameUrlImageDao.create(game.getGameId(),urlImage.toString());
                            }
                        }
                    }
                }

            } catch (IOException ex) {

            }
        }
        args.put("game_id", game.getGameId());
        final Number tournamentId = jdbcInsert.executeAndReturnKey(args);
        return new Tournament(name,tournamentId.longValue(), game.getGameId());
    }

    public Tournament create(String name, String gameName,int tier) {
        final Map<String, Object> args = new HashMap<>();
        args.put("name", name);
        args.put("is_finished", false);
        args.put("tier", tier);
        Game game = gameDao.findByName(gameName);
        if(game == null) {
            game = gameDao.create(gameName, true);
        }
        args.put("game_id", game.getGameId());
        final Number tournamentId = jdbcInsert.executeAndReturnKey(args);
        return new Tournament(name,game.getGameId(),tournamentId.longValue());
    }


    @Override
    public List<Tournament> findFeaturedTournaments() {

        int featured = 10;

        final List<Tournament> list = jdbcTemplate.query("SELECT * FROM tournament ORDER BY tournament_id DESC LIMIT ?", ROW_MAPPER, featured);

        if (list.isEmpty()) {
            return null;
        }

        for (Tournament t : list) {
            Integer numberOfMatches = getNumberOfMatches(t.getId());
            Integer numberOfPlayers = getNumberOfPlayers(t.getId());
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
    public List<String> findTournamentNames(String query) {
        StringBuilder sb = new StringBuilder(query);
        sb.append("%");
        return jdbcTemplate.queryForList("SELECT name FROM tournament WHERE name LIKE ?",  String.class, sb);
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

        for (Tournament t : list){
            t.setNumberOfMatches(getNumberOfMatches(t.getId()));
            t.setSize(getNumberOfPlayers(t.getId()));
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

    public void endTournament(long tournamentId) {
        jdbcTemplate.update("UPDATE tournament SET is_finished = ? WHERE tournament_id = ?", true,tournamentId);
    }

    private int getNumberOfMatches(long tournamentId){
        return jdbcTemplate.queryForObject("SELECT count(*) FROM match WHERE tournament_id = ? AND coalesce(away_player_id, 0) != ?", Integer.class, tournamentId, TournamentService.BYE_ID);
    }

    private int getNumberOfPlayers(long tournamentId){
        return jdbcTemplate.queryForObject("SELECT count(*) FROM participates_in WHERE tournament_id = ? AND player_id != ?", Integer.class, tournamentId, TournamentService.BYE_ID);
    }

}
