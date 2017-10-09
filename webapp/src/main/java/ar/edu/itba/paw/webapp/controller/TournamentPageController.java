package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.MatchService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Standing;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.form.MatchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@Controller
public class TournamentPageController {

    @Autowired
    private TournamentService ts;

    @Autowired
    private MatchService ms;

    @RequestMapping("/tournament/{tournamentId}")
    public ModelAndView tournament(@ModelAttribute("matchForm") final MatchForm form, @PathVariable long tournamentId){
        final ModelAndView mav = new ModelAndView("tournament");
        final Tournament t = ts.findById(tournamentId);
        if (t == null) {
            return new ModelAndView("redirect:/404");
        }
        mav.addObject("tournament", t);
        return mav;
    }

    @RequestMapping("/tournament/{tournamentId}/standings")
    public ModelAndView tournament(@PathVariable long tournamentId){
        final ModelAndView mav = new ModelAndView("standings");
        final Tournament t = ts.findById(tournamentId);
        final List<Standing> s = ts.getStandings(tournamentId);
        if (t == null) {
            return new ModelAndView("redirect:/404");
        }
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

}
