package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class SearchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SearchController.class);

    @Autowired
    private TournamentService ts;

    @Autowired
    private RankingService rs;

    @RequestMapping("/search")
    public ModelAndView search(@ModelAttribute("searchForm") final SearchForm searchForm, @RequestParam(required = false) String query){

        LOGGER.debug("Access to search");

        if (query == null){
            return new ModelAndView("search");
        }

        String term = "";

        try {
            term = URLDecoder.decode(query,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<Tournament> tournamentList = ts.findByName(term);
        List<Ranking> rankingList = rs.findByName(term);
        ModelAndView mav = new ModelAndView("search");
        mav.addObject("tournamentResults", tournamentList);
        mav.addObject("rankingResults", rankingList);
        return mav;
    }

}
