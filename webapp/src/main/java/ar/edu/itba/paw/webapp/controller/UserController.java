package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.webapp.form.UserUpdateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
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
    private GameService gs;

    @Autowired
    private UserImageService uis;

    @Autowired
    private UserFavoriteGameService ufgs;

    @Autowired
    PlayerMeController pmc;

    @Autowired
    private ApplicationContext appContext;

    @RequestMapping("/user/{userId}")
    public ModelAndView user(@PathVariable long userId){
        final User u = us.findById(userId);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("user-page");
        final List<Tournament> tournaments = ts.findTournamentByParticipant(userId);

        mav.addObject("user", u);

        List<UserFavoriteGame> list = ufgs.getFavoriteGames(u);
        mav.addObject("favoritesGames", list);
        mav.addObject("tournaments", tournaments);
        return mav;
    }

    @RequestMapping(value = "/update/{userId}", method = {RequestMethod.POST})
    public final ModelAndView updateImage(@Valid@ModelAttribute("userUpdateForm") final UserUpdateForm form, final BindingResult errors,
                                          @PathVariable long userId, @ModelAttribute("loggedUser") User loggedUser){
        if(errors.hasErrors()){
            return userSettings(form, userId);
        }

        User u = us.findById(userId);

        if(u == null) {
            return new ModelAndView("redirect:/404");
        }

        if(userId != loggedUser.getId()) {
            return new ModelAndView("redirect:/403");
        }

        if(form.getImage() != null && form.getImage().getSize() != 0) {
            try {
                uis.updateImage(u, form.getImage().getBytes());
            } catch (IOException e) {

            }
        }

        us.updateDescription(u, form.getDescription());

        Game g = gs.findByName(form.getGame());
        if(g == null) {
            g = pmc.addGameImage(form.getGame());
        }
        ufgs.deleteAll(u);
        ufgs.create(u, g);



        return new ModelAndView("redirect:/user/" + userId);
    }

    @RequestMapping("/user/{userId}/settings")
    public ModelAndView userSettings(@ModelAttribute("userUpdateForm") final UserUpdateForm form, @PathVariable long userId){
        final User u = us.findById(userId);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("user-config");

        List<UserFavoriteGame> fg = ufgs.getFavoriteGames(u);
        if(fg == null || fg.isEmpty()) {
            mav.addObject("gameName", null);
        } else {
            mav.addObject("gameName", fg.get(0).getGame().getName());
        }
        mav.addObject("user", u);
        return mav;
    }

    @RequestMapping("/user/{userId}/creates")
    public ModelAndView userCreates(@PathVariable long userId) {
        final User u = us.findById(userId);
        if (u == null) {
            return new ModelAndView("redirect:/404");
        }
        final List<Tournament> tournaments = ts.findTournamentByUser(userId);
        final List<Ranking> rankings = rs.findRankingByUser(userId);
        final ModelAndView mav = new ModelAndView("user-create");

        mav.addObject("user", u);
        mav.addObject("rankings", rankings);
        mav.addObject("tournaments", tournaments);

        List<UserFavoriteGame> list = ufgs.getFavoriteGames(u);
        mav.addObject("favoritesGames", list);
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

        List<UserFavoriteGame> list = ufgs.getFavoriteGames(u);
        mav.addObject("favoritesGames", list);
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

        List<UserFavoriteGame> list = ufgs.getFavoriteGames(u);
        mav.addObject("favoritesGames", list);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value="/profile-image/{userId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] avatar(@PathVariable(value="userId") final long userId) throws IOException {

        UserImage ui = uis.findById(userId);
        if(ui != null) {
            return ui.getImage();
        } else {
            Resource r = appContext.getResource("/resources/img/user-profile-image.jpg");
            long l = r.contentLength();
            byte[] ans = new byte[(int)l];
            r.getInputStream().read(ans);
            return ans;
        }
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
