package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.GameService;
import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.model.Game;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.persistence.GameUrlImageJdbcDao;
import ar.edu.itba.paw.webapp.form.RankingForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(RankingController.class);

    @Autowired
    private RankingService rs;

    @Autowired
    private PlayerMeController pmc;

    @RequestMapping("/ranking")
    public ModelAndView ranking(@ModelAttribute("rankingForm") final RankingForm rankingForm) {
        final ModelAndView mav = new ModelAndView("ranking");
        LOGGER.debug("Access to ranking");
        return mav;
    }

    @RequestMapping(value = "/create/ranking", method = {RequestMethod.POST})
    public ModelAndView createRanking(@Valid @ModelAttribute("rankingForm") final RankingForm rankingForm, final BindingResult errors) {

        if (errors.hasErrors()) {
            return ranking(rankingForm);
        }

        Map<Tournament, Integer> tMap = new HashMap<>();
        Game game = pmc.addGameImage(rankingForm.getGame());

        String gameName = rankingForm.getGame();

        Ranking r = rs.create(rankingForm.getRankingName(), tMap, rankingForm.getGame());
        LOGGER.info("Created Ranking with id {}", r.getId());

        return new ModelAndView("redirect:/ranking/" + r.getId());
    }

}
