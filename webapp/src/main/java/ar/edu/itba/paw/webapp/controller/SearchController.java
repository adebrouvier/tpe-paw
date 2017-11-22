package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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

    @Autowired
    private UserService us;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public ModelAndView search(@RequestParam(value = "query", required = false) String query,
                               @RequestParam(value = "game", required = false) String game,
                               @ModelAttribute("loggedUser") User loggedUser) {

        LOGGER.debug("Access to search");
        ModelAndView mav = new ModelAndView("search");
        mav.addObject("loggedUser", loggedUser);
        if (query == null) {
            return mav;
        }

        String term = "";

        try {
            term = URLDecoder.decode(query, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("UTF-8 decode error");
        }

        List<Tournament> tournamentList = ts.findByName(term, game);
        List<Ranking> rankingList = rs.findByName(term, game);
        mav.addObject("tournamentResults", tournamentList);
        mav.addObject("rankingResults", rankingList);
        return mav;
    }

    @ModelAttribute("loggedUser")
    public User loggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return us.findByName(username);
    }

}
