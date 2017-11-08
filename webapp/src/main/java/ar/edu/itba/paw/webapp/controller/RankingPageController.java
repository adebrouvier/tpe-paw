package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.TournamentPoints;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.RankingPageForm;
import ar.edu.itba.paw.webapp.form.SearchForm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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

    @Autowired
    private UserService us;

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

    @RequestMapping(value = "/delete/ranking/{rankingId}/{tournamentId}", method = {RequestMethod.POST})
    public ModelAndView deleteTournament(@PathVariable long rankingId, @PathVariable long tournamentId, @ModelAttribute("loggedUser") User loggedUser){
        final Ranking r = rs.findById(rankingId);
        if (r == null) {
            return new ModelAndView("redirect:/404");
        }
        if (r.getUser().getId() != loggedUser.getId()){
            LOGGER.warn("Unauthorized User {} tried to add tournament to ranking {}", loggedUser.getId(), rankingId);
            return new ModelAndView("redirect:/403");
        }
        rs.delete(rankingId,tournamentId);
        LOGGER.info("Deleted tournament {} from ranking {}", tournamentId, rankingId);
        return  new ModelAndView("redirect:/ranking/" + r.getId());
    }

    @RequestMapping(value = "/update/ranking/{rankingId}/addtournament", method = {RequestMethod.POST})
    public ModelAndView addTournament(@PathVariable long rankingId, @Valid @ModelAttribute("rankingPageForm") final RankingPageForm rankingPageForm, final BindingResult errors, @ModelAttribute("loggedUser") User loggedUser) {

        if (errors.hasErrors()) {
            return rankingPage(rankingPageForm, rankingId);
        }

        final Ranking ranking = rs.findById(rankingId);

        if (ranking == null) {
            return new ModelAndView("redirect:/404");
        }

        if (ranking.getUser().getId() != loggedUser.getId()){
            LOGGER.warn("Unauthorized User {} tried to add tournament to ranking {}", loggedUser.getId(), rankingId);
            return new ModelAndView("redirect:/403");
        }

        Map<Tournament, Integer> tMap = new HashMap<>();
        final Tournament tournament = ts.getByNameAndGameId(rankingPageForm.getTournamentName(), ranking.getGame().getId());

        if (tournament == null){
            errors.rejectValue("tournamentName","rankingPageForm.tournamentName.error.exist");
            return rankingPage(rankingPageForm, rankingId);
        } else if (tournament.getGame().getId() != ranking.getGame().getId()){
            errors.rejectValue("tournamentName","rankingPageForm.tournamentName.error.game");
            return rankingPage(rankingPageForm, rankingId);
        } else if (tournament.getStatus() != Tournament.Status.FINISHED){
            errors.rejectValue("tournamentName","rankingPageForm.tournamentName.error.notFinished");
            return rankingPage(rankingPageForm, rankingId);
        } else{
            for(TournamentPoints tPoints: ranking.getTournaments()){
                if(tournament.getId() == tPoints.getTournament().getId()){
                    errors.rejectValue("tournamentName","rankingPageForm.tournamentName.error.duplicateTournament");
                    return rankingPage(rankingPageForm,rankingId);
                }
            }
        }
        tMap.put(tournament,rankingPageForm.getPoints());
        rs.addTournaments(rankingId,tMap);
        LOGGER.info("Added tournament {} to ranking {}", tournament.getId(), rankingId);

        return new ModelAndView("redirect:/ranking/" + ranking.getId());
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
