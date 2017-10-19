package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.webapp.form.TournamentSearchForm;
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

    @Autowired
    private TournamentService ts;

    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("searchForm") final TournamentSearchForm searchForm) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("tournaments",ts.findFeaturedTournaments());
        return mav;
    }

    @RequestMapping(value = "/search_autocomplete", method = RequestMethod.GET)
    public @ResponseBody List<String> searchAutocomplete(@RequestParam("query") String query) {
        return ts.findTournamentNames(query);
    }

    @RequestMapping(value = "/searchtournament", method = { RequestMethod.GET })
    public ModelAndView searchtournament(@Valid @ModelAttribute("searchForm")
                               final TournamentSearchForm form, final BindingResult errors) {
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
