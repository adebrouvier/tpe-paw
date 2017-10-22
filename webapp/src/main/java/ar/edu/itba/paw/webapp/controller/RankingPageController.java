package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.form.RankingForm;
import ar.edu.itba.paw.webapp.form.RankingPageForm;
import ar.edu.itba.paw.webapp.form.RankingTournaments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RankingPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RankingPageController.class);

    @Autowired
    private RankingService rs;

    @Autowired
    private TournamentService ts;

    @RequestMapping("/ranking/{rankingId}")
    public ModelAndView rankingPage(@ModelAttribute("rankingPageForm") final RankingPageForm rankingPageForm, @PathVariable long rankingId){
        LOGGER.debug("Access to ranking id {}", rankingId);
        final Ranking r = rs.findById(rankingId);
        if (r == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("rankingPage");
        mav.addObject("ranking", r);
        return mav;
    }

    @RequestMapping("/ranking/{rankingId}/delete/{tournamentId}")
    public ModelAndView deleteTournament(@PathVariable long rankingId, @PathVariable long tournamentId){
        rs.delete(rankingId,tournamentId);
        final Ranking r = rs.findById(rankingId) ;
        if (r == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("redirect:/ranking/" + r.getId());
        mav.addObject("ranking", r);
        return mav;
    }

    @RequestMapping(value = "/ranking/{rankingId}/addPlayers", method = {RequestMethod.POST})
    public ModelAndView createRanking(@Valid @ModelAttribute("rankingPageForm") final RankingPageForm rankingPageForm, final BindingResult errors,@PathVariable long rankingId) {

        if (errors.hasErrors()) {
            //TODO: Queda en el url el addPlayer
            return rankingPage(rankingPageForm, rankingId);
        }

        Map<Tournament, Integer> tMap = new HashMap<>();

        for (RankingTournaments rt : rankingPageForm.getTournaments()){
            if(rt.getName() != ""){
                final Tournament t = ts.getByName(rt.getName());
                if (t != null) {
                    tMap.put(t, rt.getPoints());
                }
            }else{
                errors.rejectValue("tournaments","Empty Tournament");
            }
        }

        Ranking r = rs.addTournaments(rankingId,tMap);

        return new ModelAndView("redirect:/ranking/" + r.getId());
    }

}
