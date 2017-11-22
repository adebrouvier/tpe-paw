package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private TournamentService ts;

    @Autowired
    private RankingService rs;

    @Autowired
    private UserService us;

    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("loggedUser") User loggedUser) {
        LOGGER.debug("Access to index");
        final ModelAndView mav = new ModelAndView("index");
        int featuredTournaments = 5;
        int featuredRankings = 5;
        int top = 3;
        mav.addObject("tournaments", ts.findFeaturedTournaments(featuredTournaments));
        mav.addObject("rankings", rs.findFeaturedRankings(featuredRankings));
        mav.addObject("loggedUser", loggedUser);
        mav.addObject("topUsers", us.findTopWinners(top));
        mav.addObject("mostFollowed", us.findMostFollowed(top));
        mav.addObject("popularRankings", rs.findPopularRankings(top));
        return mav;
    }

    /**
     * Used by Bloodhound to get tournament names
     * @param query term to search
     * @return an array of tournament names
     */
    @RequestMapping(value = "/tournament_autocomplete", method = RequestMethod.GET)
    public @ResponseBody List<String> tournamentAutocomplete(@RequestParam("query") String query, @RequestParam(value = "game", required = false) Long game) {

        if (game == null){
            return ts.findTournamentNames(query);
        }else{
            return ts.findTournamentNames(query, game);
        }
    }

    /**
     * Used by Bloodhound to get ranking names
     * @param query term to search
     * @return an array of ranking names
     */
    @RequestMapping(value = "/ranking_autocomplete", method = RequestMethod.GET)
    public @ResponseBody List<String> rankingAutocomplete(@RequestParam("query") String query) {
        return rs.findRankingNames(query);
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
