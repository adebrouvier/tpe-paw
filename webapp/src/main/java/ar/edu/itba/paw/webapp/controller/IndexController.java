package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.form.TournamentForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

@Controller
public class IndexController {

    @Autowired
    private TournamentService ts;

    @Autowired
    private PlayerService ps;
    
    @Autowired
    private RankingService rs;

    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("tournamentForm") final TournamentForm form) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("tournaments",ts.findFeaturedTournaments());
        return mav;
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("tournamentForm")
                                   final TournamentForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(form);
        }
        final List<Player> players = parsePlayers(form.getPlayers());
        if (form.isRandomizeSeed()) {
            Collections.shuffle(players);
        }
        final Tournament t = ts.create(form.getTournamentName(),players);
        /*final Tournament t1 = ts.create("Prueba",players); //TODO: remove. Only for testing
        t1.setId(20);
        Map<Tournament,Integer> tMap = new HashMap<>();
        tMap.put(t,10);
        tMap.put(t1,100);
        final Ranking r = rs.create("Test",tMap);*/
        return new ModelAndView("redirect:/tournament/"+ t.getId());
    }

    private List<Player> parsePlayers(String players) {

        List<Player> result = new ArrayList<>();

        for (String player : players.split("\r\n")){

            if (player.length() > 0) {

                final Player p = ps.create(player);

                result.add(p);
            }
        }

        return result;
    }
}
