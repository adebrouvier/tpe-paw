package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.form.RankingForm;
import ar.edu.itba.paw.webapp.form.RankingTournaments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class RankingController {

    @Autowired
    private TournamentService ts;

    @Autowired
    private RankingService rs;

    @RequestMapping("/ranking")
    public ModelAndView ranking(@ModelAttribute("rankingForm") final RankingForm rankingForm) {
        final ModelAndView mav = new ModelAndView("ranking");
        return mav;
    }

    @RequestMapping(value = "/create/ranking", method = {RequestMethod.POST})
    public ModelAndView createRanking(@Valid @ModelAttribute("rankingForm") final RankingForm rankingForm, final BindingResult errors) {

        if (errors.hasErrors()) {
            return ranking(rankingForm);
        }

        Map<Tournament, Integer> tMap = new HashMap<>();

        for (RankingTournaments rt : rankingForm.getTournaments()){

            final Tournament t = ts.getByName(rt.getName());
            if (t != null) {
                tMap.put(t, rt.getPoints());
            }
        }

        Ranking r = rs.create(rankingForm.getRankingName(), tMap);

        return new ModelAndView("redirect:/ranking/" + r.getId());
    }

}
