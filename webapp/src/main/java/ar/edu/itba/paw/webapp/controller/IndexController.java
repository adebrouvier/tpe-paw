package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.form.TournamentForm;
import ar.edu.itba.paw.webapp.form.TournamentSearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private TournamentService ts;

    @Autowired
    private PlayerService ps;

    @RequestMapping("/")
    public ModelAndView index(@ModelAttribute("tournamentForm") final TournamentForm form, @ModelAttribute("searchForm") final TournamentSearchForm searchForm) {
        final ModelAndView mav = new ModelAndView("index");
        mav.addObject("tournaments",ts.findFeaturedTournaments());
        mav.addObject("tournamentNames", tournamentToCellString(ts.findTournamentNames()));
        return mav;
    }

    private String tournamentToCellString(List<String> list){
        StringBuilder sb = new StringBuilder("{");
        for(String tournament : list) {
            sb.append("\"");
            sb.append(tournament);
            sb.append("\"");
            sb.append(": null,");
        }

        sb.append("\"other\":null}");

        return sb.toString();
    }

    @RequestMapping(value = "/create", method = { RequestMethod.POST })
    public ModelAndView create(@Valid @ModelAttribute("tournamentForm")
                                   final TournamentForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(form,null);
        }
        final List<Player> players = parsePlayers(form.getPlayers());
        if (form.isRandomizeSeed()) {
            Collections.shuffle(players);
        }
        final Tournament t = ts.create(form.getTournamentName(),players);
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

    @RequestMapping(value = "/searchtourn", method = { RequestMethod.GET })
    public ModelAndView search(@Valid @ModelAttribute("searchForm")
                               final TournamentSearchForm form, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(null, form);
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
