package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.GameService;
import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Game;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Standing;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.form.MatchForm;
import ar.edu.itba.paw.webapp.form.TournamentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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

    @RequestMapping("/tournament")
    public ModelAndView tournament(@ModelAttribute("tournamentForm") final TournamentForm form) {
        final ModelAndView mav = new ModelAndView("tournament");
        mav.addObject("games", gameToString(gs.findGamesName()));
        return mav;
    }

    private String gameToString(List<String> list){
        StringBuilder sb = new StringBuilder("{");
        if (list == null) {
            return sb.append("}").toString();
        } else if (list.isEmpty()) {
            return sb.append("}").toString();
        }
        int size = list.size();
        int i = 0;
        for( ; i < size-1 ; i++) {
            sb.append("\"").append(list.get(i)).append("\": null,");
        }

        sb.append("\"").append(list.get(i)).append("\":null}");
        return sb.toString();
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
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
