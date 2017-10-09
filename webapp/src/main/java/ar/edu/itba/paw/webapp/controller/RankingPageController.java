package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.webapp.form.RankingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RankingPageController {

    @Autowired
    private TournamentService ts;

    @Autowired
    private RankingService rs;

    @RequestMapping("/ranking/{rankingId}")
    public ModelAndView rankingPage(@ModelAttribute("rankingForm") final RankingForm rankingForm, @PathVariable long rankingId){
        final ModelAndView mav = new ModelAndView("rankingPage");
        final Ranking r = rs.findById(rankingId);
        if (r== null) {
            return new ModelAndView("redirect:/404");
        }
        mav.addObject("ranking", r);
        return mav;
    }
}
