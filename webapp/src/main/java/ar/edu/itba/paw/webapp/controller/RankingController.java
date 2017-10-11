package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.form.RankingForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RankingController {

    @Autowired
    private TournamentService ts;

    @Autowired
    private PlayerService ps;

    @Autowired
    private RankingService rs;

    @RequestMapping("/ranking")
    public ModelAndView ranking(@ModelAttribute("rankingForm") final RankingForm rankingForm) {

        final ModelAndView mav = new ModelAndView("ranking");
        return mav;

    }

    @RequestMapping(value = "/createRanking", method = {RequestMethod.POST})
    public ModelAndView createRanking(@Valid @ModelAttribute("rankingForm") final RankingForm rankingForm, final BindingResult errors) {
        /*Tournament t = ts.findById(rankingForm.getTournaments());
        Map<Tournament,Integer> tMap = new HashMap<>();
        tMap.put(t,rankingForm.getAwardedPoints());
        Ranking r = rs.create(rankingForm.getRankingName(),tMap);*/

        if (errors.hasErrors()) {
            ranking(rankingForm);
        }

        Map<Tournament, Integer> tMap = new HashMap<>();
        int size = 0;
        if (rankingForm.getTournaments() != null)
            size = rankingForm.getTournaments().size();
        for (int i = 0; i < size; i++) {

            String name = rankingForm.getTournaments().get(i);

            final Tournament t = ts.getByName(name);
            if (t != null) {
                tMap.put(t, rankingForm.getPoints().get(i));
            }
        }

        /*List<Tournament> tournaments = parseTournaments(rankingForm.getTournaments());
        for(Tournament tournament : tournaments){
            tMap.put(tournament,100);
        }*/
        Ranking r = rs.create(rankingForm.getRankingName(), tMap);
        //TODO ranking Page
        return new ModelAndView("redirect:/ranking/" + r.getId());
    }

    private List<Tournament> parseTournaments(String tournaments) {

        List<Tournament> result = new ArrayList<>();

        Long id;

        for (String tournament : tournaments.split("\r\n")) {

            if (tournament.length() > 0) {

                id = Long.parseLong(tournament);
                final Tournament t = ts.findById(id);

                if (t != null) {
                    result.add(t);
                }

            }
        }

        return result;
    }

}
