package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService us;

    @Autowired
    private TournamentService ts;

    @Autowired
    private RankingService rs;

    @Autowired
    private ApplicationContext appContext;

    @RequestMapping("/user/{userId}")
    public ModelAndView user(@PathVariable long userId){
        final User u = us.findById(userId);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }
        //final List<Tournament> tournaments = ts.findUserTournament(userId);
        //final List<Ranking> rankings = rs.findUserRanking(userId);

        final ModelAndView mav = new ModelAndView("user-page");

        mav.addObject("user", u);
        //mav.addObject("rankings", rankings);
        //mav.addObject("tournaments", tournaments);
        return mav;
    }

    @RequestMapping("/user/{userId}/creates")
    public ModelAndView userCreates(@PathVariable long userId){
        final User u = us.findById(userId);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("user-create");

        mav.addObject("user", u);
        return mav;
    }

    @RequestMapping("/user/{userId}/followers")
    public ModelAndView userFollowers(@PathVariable long userId){
        final User u = us.findById(userId);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("user-followers");

        mav.addObject("user", u);
        return mav;
    }

    @RequestMapping("/user/{userId}/followed")
    public ModelAndView userFollowed(@PathVariable long userId){
        final User u = us.findById(userId);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("user-followed");

        mav.addObject("user", u);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value="/profile-image/{userId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] avatar(@PathVariable(value="userId") final long userId) throws IOException {

        Resource r = appContext.getResource("/resources/img/user-profile-image.jpg");
        long l = r.contentLength();
        byte[] ans = new byte[(int)l];
        r.getInputStream().read(ans);
        return ans;

    }

}
