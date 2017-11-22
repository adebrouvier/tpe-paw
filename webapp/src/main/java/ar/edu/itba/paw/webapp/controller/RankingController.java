package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.RankingForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RankingController.class);

    @Autowired
    private RankingService rs;

    @Autowired
    private PlayerMeController pmc;

    @Autowired
    private UserService us;

    @RequestMapping("/ranking")
    public ModelAndView ranking(@ModelAttribute("rankingForm") final RankingForm rankingForm, @ModelAttribute("loggedUser") User loggedUser) {
        final ModelAndView mav = new ModelAndView("ranking");
        mav.addObject("loggedUser", loggedUser);
        LOGGER.debug("Access to ranking");
        return mav;
    }

    @RequestMapping(value = "/create/ranking", method = {RequestMethod.POST})
    public ModelAndView createRanking(@Valid @ModelAttribute("rankingForm") final RankingForm rankingForm, final BindingResult errors, @ModelAttribute("loggedUser") User loggedUser) {

        if (errors.hasErrors()) {
            return ranking(rankingForm,loggedUser);
        }

        Map<Tournament, Integer> tMap = new HashMap<>();
        pmc.addGameImage(rankingForm.getGame());

        Ranking r = rs.create(rankingForm.getRankingName(), tMap, rankingForm.getGame(), loggedUser().getId());
        LOGGER.info("Created Ranking with id {}", r.getId());

        return new ModelAndView("redirect:/ranking/" + r.getId());
    }

    @ModelAttribute("loggedUser")
    public User loggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return us.findByName(username);
    }

}
