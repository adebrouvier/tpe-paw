package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.webapp.form.UserUpdateForm;
import org.jetbrains.annotations.NotNull;
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
    private PlayerMeController pmc;

    @Autowired
    private UserFollowService ufs;

    @Autowired
    private ApplicationContext appContext;

    @RequestMapping("/user/name/{userName}")
    public ModelAndView user(@PathVariable String userName, @ModelAttribute("loggedUser") User loggedUser){
        final User u = us.findByName(userName);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }
        ModelAndView mav = new ModelAndView("redirect:/user/" + u.getId());
        mav.addObject("loggedUser", loggedUser);
        return mav;
    }

    @RequestMapping("/user/{userId}")
    public ModelAndView user(@PathVariable long userId, @ModelAttribute("loggedUser") User loggedUser){
        final User u = us.findById(userId);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("user-page");
        final List<Tournament> tournaments = ts.findTournamentByParticipant(userId, 0);

        mav.addObject("user", u);

        List<UserFavoriteGame> list = ufgs.getFavoriteGames(u);
        mav.addObject("favoritesGames", list);
        mav.addObject("tournaments", tournaments);
        mav.addObject("isFollow", ufs.isFollow(loggedUser(), u));
        mav.addObject("loggedUser", loggedUser);

        return mav;
    }


    @RequestMapping("/user/{userId}/{page}")
    public ModelAndView userPagination(@PathVariable long userId, @PathVariable int page){
        final User u = us.findById(userId);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }
        final ModelAndView mav = new ModelAndView("message-user-page");
        final List<Tournament> tournaments = ts.findTournamentByParticipant(userId, page);

        mav.addObject("user", u);
        mav.addObject("tournaments", tournaments);

        return mav;
    }

    @NotNull
    @RequestMapping(value = "user/{userId}/follow")
    public final ModelAndView followUser(@PathVariable long userId, @ModelAttribute("loggedUser") User loggedUser) {
        User u = us.findById(userId);

        if(u == null) {
            return new ModelAndView("redirect:/404");
        }

        if(loggedUser == null || userId == loggedUser.getId()) {
            return new ModelAndView("redirect:/403");
        }

        ufs.create(loggedUser, u);

        return new ModelAndView("redirect:/user/" + userId);
    }

    @NotNull
    @RequestMapping(value = "user/{userId}/unfollow")
    public final ModelAndView unfollowUser(@PathVariable long userId, @ModelAttribute("loggedUser") User loggedUser) {
        User u = us.findById(userId);

        if(u == null) {
            return new ModelAndView("redirect:/404");
        }

        if(loggedUser == null || userId == loggedUser.getId()) {
            return new ModelAndView("redirect:/403");
        }

        ufs.delete(loggedUser, u);

        return new ModelAndView("redirect:/user/" + userId);
    }

    @RequestMapping(value = "/update/{userId}", method = {RequestMethod.POST})
    public final ModelAndView updateImage(@Valid@ModelAttribute("userUpdateForm") final UserUpdateForm form, final BindingResult errors,
                                          @PathVariable long userId, @ModelAttribute("loggedUser") User loggedUser){
        if(errors.hasErrors()){
            return userSettings(form, userId, loggedUser);
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

        us.updateDescription(u, form.getDescription(),form.getTwitchUrl(),form.getTwitterUrl(),form.getYoutubeUrl());

        Game g = gs.findByName(form.getGame());
        if(g == null) {
            g = pmc.addGameImage(form.getGame());
        }
        ufgs.deleteAll(u);
        ufgs.create(u, g);



        return new ModelAndView("redirect:/user/" + userId);
    }

    @RequestMapping("/user/{userId}/settings")
    public ModelAndView userSettings(@ModelAttribute("userUpdateForm") final UserUpdateForm form, @PathVariable long userId, @ModelAttribute("loggedUser") User loggedUser){
        final User u = us.findById(userId);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }

        if(loggedUser == null || loggedUser.getId() != u.getId()) {
            return new ModelAndView("redirect:/403");
        }
        final ModelAndView mav = new ModelAndView("user-config");

        List<UserFavoriteGame> fg = ufgs.getFavoriteGames(u);
        if(fg == null || fg.isEmpty()) {
            mav.addObject("gameName", null);
        } else {
            mav.addObject("gameName", fg.get(0).getGame().getName());
        }
        mav.addObject("user", u);
        mav.addObject("loggedUser", loggedUser);

        return mav;
    }

    @RequestMapping("/user/{userId}/creates")
    public ModelAndView userCreates(@PathVariable long userId, @ModelAttribute("loggedUser") User loggedUser) {
        final User u = us.findById(userId);
        if (u == null) {
            return new ModelAndView("redirect:/404");
        }
        final List<Tournament> tournaments = ts.findTournamentByUser(userId, 0);
        final List<Ranking> rankings = rs.findRankingByUser(userId);
        final ModelAndView mav = new ModelAndView("user-create");

        mav.addObject("user", u);
        mav.addObject("rankings", rankings);
        mav.addObject("tournaments", tournaments);
        mav.addObject("isFollow", ufs.isFollow(u, loggedUser()));

        List<UserFavoriteGame> list = ufgs.getFavoriteGames(u);
        mav.addObject("favoritesGames", list);
        mav.addObject("loggedUser", loggedUser);

        return mav;
    }

    @RequestMapping("/user/{userId}/{page}/creates")
    public ModelAndView userCreatesPagination(@PathVariable long userId, @PathVariable int page) {
        final User u = us.findById(userId);
        if (u == null) {
            return new ModelAndView("redirect:/404");
        }
        final List<Tournament> tournaments = ts.findTournamentByUser(userId, page);
        //final List<Ranking> rankings = rs.findRankingByUser(userId, page);
        final ModelAndView mav = new ModelAndView("message-user-create");

        mav.addObject("user", u);
        //mav.addObject("rankings", rankings);
        mav.addObject("tournaments", tournaments);

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
