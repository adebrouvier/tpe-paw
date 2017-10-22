package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.webapp.form.MatchForm;
import ar.edu.itba.paw.webapp.form.PlayerForm;
import ar.edu.itba.paw.webapp.form.TournamentForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class TournamentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentController.class);

    @Autowired
    private PlayerService ps;

    @Autowired
    private TournamentService ts;

    @Autowired
    private MatchService ms;

    @Autowired
    private GameService gs;

    @Autowired
    private UserService us;

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
    public ModelAndView create(@Valid @ModelAttribute("tournamentForm") final TournamentForm form,
                               @ModelAttribute("loggedUser") User loggedUser,
                               final BindingResult errors) {
        if (errors.hasErrors()) {
            return tournament(form);
        }
        final Tournament t = ts.create(form.getTournamentName(), form.getGame(), loggedUser.getId());
        return new ModelAndView("redirect:/tournament/"+ t.getId() + "/players");
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

    @RequestMapping("/tournament/{tournamentId}/players")
    public ModelAndView tournament(@ModelAttribute("playerForm") PlayerForm playerForm, @PathVariable long tournamentId){
        final Tournament t = ts.findById(tournamentId);

        if (t == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("players");
        final Game game = gs.findById(t.getGameId());
        if(game == null) {
            mav.addObject("game", new Game(0, ""));
        }
        final List<Player> playerList = ps.getTournamentPlayers(tournamentId);
        mav.addObject("game", game);
        mav.addObject("tournament", t);
        mav.addObject("players", playerList);

        return mav;
    }

    @RequestMapping( value = "/tournament/{tournamentId}/players", method = RequestMethod.POST)
    public ModelAndView addPlayer(@ModelAttribute("playerForm") PlayerForm playerForm, @PathVariable long tournamentId, final BindingResult errors){

        if (errors.hasErrors()){
            return tournament(playerForm, tournamentId);
        }

        String username = playerForm.getUsername();
        final User user;
        final Player p;

        /* If username field is not empty */
        if (username != null) {
            user = us.findByName(playerForm.getUsername());

            /* If user doesn't exist */
            if (user == null) {
                errors.rejectValue("username", "playerForm.error.username");
                return tournament(playerForm, tournamentId);
            }else{ /* Player linked to user */
                p = ps.create(playerForm.getPlayer(), user.getId());
            }
        }else{ /* Player is not linked to user */
            p = ps.create(playerForm.getPlayer());
        }

        /*if (form.isRandomizeSeed()) {
            Collections.shuffle(players);
        }*/

        ps.addToTournament(p.getId(), tournamentId);

        return new ModelAndView("redirect:/tournament/"+ tournamentId +"/players");
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

    @RequestMapping(value = "/tournament/{tournamentId}/end", method = { RequestMethod.POST })
    public ModelAndView endTournament(@PathVariable long tournamentId) {
        ts.setStatus(tournamentId, Tournament.Status.FINISHED);
        return new ModelAndView("redirect:/tournament/" + tournamentId);
    }

    @RequestMapping(value = "/remove/player/{tournamentId}/{playerId}", method = { RequestMethod.POST })
    public ModelAndView removePlayer(@PathVariable long tournamentId, @PathVariable int playerId) {

        if(ps.removeToTournament(tournamentId, playerId)) {
            ps.delete(playerId);
        }
        return new ModelAndView("redirect:/tournament/"+ tournamentId + "/players");
    }

    @RequestMapping(value = "/swap/player/{tournamentId}/{playerOldSeed}/{playerNewSeed}", method = { RequestMethod.POST })
    public ModelAndView swapPlayer(@PathVariable long tournamentId, @PathVariable int playerOldSeed,@PathVariable int playerNewSeed) {
        ps.changeSeedToTournament(tournamentId, playerOldSeed, playerNewSeed);
        return new ModelAndView("redirect:/tournament/"+ tournamentId + "/players");
    }

    @RequestMapping(value ="/tournament/{tournamentId}/generate", method = {RequestMethod.POST})
    public ModelAndView generateBracket(@PathVariable long tournamentId){
        ts.generateBracket(tournamentId);
        ts.setStatus(tournamentId, Tournament.Status.STARTED);
        LOGGER.debug("Generated bracket for tournament {}", tournamentId);
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

    /*
       Empty strings as null for optional fields
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute("loggedUser")
    public User loggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return us.findByName(username);
    }

}
