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
    public ModelAndView index(@ModelAttribute("searchForm") final SearchForm searchForm) {
        LOGGER.debug("Access to index");
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("tournaments",ts.findFeaturedTournaments());
        return mav;
    }

    /**
     * Used by Bloodhound to get tournament names
     * @param query term to search
     * @return an array of tournament names
     */
    @RequestMapping(value = "/tournament_autocomplete", method = RequestMethod.GET)
    public @ResponseBody List<String> tournamentAutocomplete(@RequestParam("query") String query) {
        return ts.findTournamentNames(query);
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

    @RequestMapping(value = "/searchParse", method = { RequestMethod.GET })
    public ModelAndView searchParse(@Valid @ModelAttribute("searchForm")
                               final SearchForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(form);
        }

        String search = form.getQuery();
        String param = "";
        try {
            param = URLEncoder.encode(search,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return new ModelAndView("redirect:/search?query=" + param);
    }
}
