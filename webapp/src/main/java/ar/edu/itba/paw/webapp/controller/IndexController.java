package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class IndexController {

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private TournamentService ts;

    @Autowired
    private RankingService rs;

    @RequestMapping("/")
    public ModelAndView index() {
        LOGGER.debug("Access to index");
        final ModelAndView mav = new ModelAndView("index");
        int featuredTournaments = 5;
        int featuredRankings = 5;
        mav.addObject("tournaments", ts.findFeaturedTournaments(featuredTournaments));
        mav.addObject("rankings", rs.findFeaturedRankings(featuredRankings));
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

}
