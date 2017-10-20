package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.webapp.form.MatchForm;
import ar.edu.itba.paw.webapp.form.TournamentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class TournamentController {

    @Autowired
    private PlayerService ps;

    @Autowired
    private TournamentService ts;

    @Autowired
    private MatchService ms;

    @Autowired
    private GameService gs;

    @Autowired
    private GameImageService gis;

    @RequestMapping("/tournament")
    public ModelAndView tournament(@ModelAttribute("tournamentForm") final TournamentForm form) {
        final ModelAndView mav = new ModelAndView("tournament");
        return mav;
    }

    @RequestMapping(value = "/games_autocomplete", method = RequestMethod.GET)
    public @ResponseBody
    List<String> gamesAutocomplete(@RequestParam("query") String query) {
        return gs.findGameNames(query);
    }

    @RequestMapping(value = "/create/tournament", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("tournamentForm")
                               final TournamentForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return tournament(form);
        }
        final List<Player> players = parsePlayers(form.getPlayers());
        if (form.isRandomizeSeed()) {
            Collections.shuffle(players);
        }
        final Tournament t = ts.create(form.getTournamentName(),players, form.getGame());
        return new ModelAndView("redirect:/tournament/"+ t.getId());
    }

    @RequestMapping("/tournament/{tournamentId}")
    public ModelAndView tournament(@ModelAttribute("matchForm") final MatchForm form, @PathVariable long tournamentId){
        final Tournament t = ts.findById(tournamentId);
        if (t == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("tournament-page");
        final Game game = gs.findById(t.getGameId());
        if(game == null) {
            mav.addObject("game", new Game(0, ""));
        }
        mav.addObject("game", game);
        mav.addObject("tournament", t);
        return mav;
    }

    @RequestMapping("/tournament/{tournamentId}/standings")
    public ModelAndView tournament(@PathVariable long tournamentId){
        final Tournament t = ts.findById(tournamentId);
        final List<Standing> s = ts.getStandings(tournamentId);
        if (t == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("standings");
        final Game game = gs.findById(t.getGameId());
        if(game == null) {
            mav.addObject("game", new Game(0, ""));
        }
        mav.addObject("game", game);
        mav.addObject("tournament", t);
        mav.addObject("standings", s);
        return mav;
    }

    @RequestMapping(value = "/update/{tournamentId}/{matchId}", method = { RequestMethod.POST })
    public ModelAndView updateMatch(@Valid @ModelAttribute("matchForm")
                               final MatchForm form, final BindingResult errors, @PathVariable long tournamentId, @PathVariable int matchId) {
        if (errors.hasErrors()) {
            return tournament(form,tournamentId);
        }

        ms.updateScore(tournamentId,matchId,form.getHomeResult(),form.getAwayResult());

        return new ModelAndView("redirect:/tournament/"+ tournamentId);
    }

    @RequestMapping(value = "/endTournament/{tournamentId}", method = { RequestMethod.POST })
    public ModelAndView endTournament(@ModelAttribute("tournament") final TournamentForm form, @PathVariable long tournamentId) {
        ts.endTournament(tournamentId);
        return new ModelAndView("redirect:/tournament/" + tournamentId);
    }

    @ResponseBody
    @RequestMapping(value="/gameImage/{gameId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] avatar(@PathVariable(value="gameId") final long gameId) throws IOException {
        GameImage gameImage = gis.findById(gameId);
        if(gameImage == null){
            Resource r = appContext.getResource("/resources/img/cs3.jpg");
            long l = r.contentLength();
            byte[] ans = new byte[(int)l];
            r.getInputStream().read(ans);
            return ans;
        } else {
            return gameImage.getImage();
        }
    }

    private List<Player> parsePlayers(String players) {

        List<Player> result = new ArrayList<>();

        for (String player : players.split("\r\n")){

            if (player.length() > 0) {

                final Player p = ps.create(player);

                result.add(p);
            }
        }

        return result;
    }

}
