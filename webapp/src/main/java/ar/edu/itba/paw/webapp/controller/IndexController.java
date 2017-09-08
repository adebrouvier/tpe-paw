package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.form.TournamentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class IndexController {

    @Autowired
    private TournamentService ts;

    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("tournamentForm") final TournamentForm form) {
        final ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @RequestMapping("/tournament/{tournamentId}")
    public ModelAndView greeting(@PathVariable long tournamentId){
        final ModelAndView mav = new ModelAndView("tournament");
        mav.addObject("tournament", ts.findById(tournamentId));
        return mav;
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("tournamentForm")
                                   final TournamentForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(form);
        }
        final Tournament t = ts.create(form.getTournamentName(), form.getPlayers());
        return new ModelAndView("redirect:/tournament/"+ t.getId());
    }
}
