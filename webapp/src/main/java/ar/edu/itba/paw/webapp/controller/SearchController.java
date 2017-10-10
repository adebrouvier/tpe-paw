package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Tournament;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

@Controller
public class SearchController {

    @Autowired
    private TournamentService ts;

    @RequestMapping("/search")
    public ModelAndView searchQuery(@RequestParam String query){

        String term = "";

        try {
            term = URLDecoder.decode(query,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        List<Tournament> tournamentList = ts.findByName(term);
        ModelAndView mav = new ModelAndView("search");
        mav.addObject("tournamentResults", tournamentList);
        return mav;
    }

}
