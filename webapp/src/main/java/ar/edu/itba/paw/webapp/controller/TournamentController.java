package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.webapp.form.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class TournamentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TournamentController.class);

    @Autowired
    private PlayerService ps;

    @Autowired
    private TournamentService ts;

    @Autowired
    private MatchService ms;

    @Autowired
    private GameService gs;

    @Autowired
    private UserService us;

    @Autowired
    private GameImageService gis;

    @Autowired
    private PlayerMeController pmc;

    @Autowired
    private NotificationService ns;

    @Autowired
    private CommentService cs;

    @Autowired
    private InscriptionService is;

    @RequestMapping("/tournament")
    public ModelAndView tournament(@ModelAttribute("tournamentForm") final TournamentForm form) {
        final ModelAndView mav = new ModelAndView("tournament");
        LOGGER.debug("Tournament");
        return mav;
    }

    @RequestMapping(value = "/games_autocomplete", method = RequestMethod.GET)
    public @ResponseBody
    List<String> gamesAutocomplete(@RequestParam("query") String query) {
        return gs.findGameNames(query);
    }

    @RequestMapping(value = "/create/tournament", method = { RequestMethod.POST })
    public ModelAndView create(@Valid@ModelAttribute("tournamentForm") final TournamentForm form,final BindingResult errors,@ModelAttribute("loggedUser") User loggedUser) {
        if (errors.hasErrors()) {
            return tournament(form);
        }

        Game game = pmc.addGameImage(form.getGame());

        final Tournament t = ts.create(form.getTournamentName(), game.getId(), loggedUser.getId());
        LOGGER.info("Created tournament {} with id {}", t.getName(), t.getId());
        return new ModelAndView("redirect:/tournament/"+ t.getId() + "/players");
    }

    @RequestMapping("/tournament/{tournamentId}")
    public ModelAndView tournament(@ModelAttribute("matchForm") final MatchForm form, @PathVariable long tournamentId){
        final Tournament t = ts.findById(tournamentId);
        if (t == null) {
            return new ModelAndView("redirect:/404");
        }
        LOGGER.debug("Access to tournament id {}", tournamentId);

        final List<Inscription> inscriptions = is.finByTournamentId(tournamentId);

        boolean signedUp = false;

        if (loggedUser() != null) {
            signedUp = is.findByIds(loggedUser().getId(), tournamentId) != null;
        }

        final ModelAndView mav = new ModelAndView("tournament-page");
        mav.addObject("tournament", t);
        mav.addObject("inscriptions", inscriptions);
        mav.addObject("signedUp", signedUp);
        return mav;
    }

    @RequestMapping("/tournament/{tournamentId}/standings")
    public ModelAndView tournament(@PathVariable long tournamentId){
        final Tournament t = ts.findById(tournamentId);
        if (t == null) {
            return new ModelAndView("redirect:/404");
        }
        LOGGER.debug("Access to tournament {} standings", t.getId());
        final ModelAndView mav = new ModelAndView("standings");
        mav.addObject("tournament", t);
        return mav;
    }

    @RequestMapping("/tournament/{tournamentId}/players")
    public ModelAndView tournament(@ModelAttribute("playerForm") PlayerForm playerForm, @PathVariable long tournamentId){
        final Tournament t = ts.findById(tournamentId);

        if (t == null) {
            return new ModelAndView("redirect:/404");
        }

        final List<Inscription> inscriptions = is.finByTournamentId(tournamentId);

        LOGGER.debug("Access to tournament id {} players", tournamentId);
        final ModelAndView mav = new ModelAndView("players");
        mav.addObject("tournament", t);
        mav.addObject("inscriptions", inscriptions);
        return mav;
    }

    @RequestMapping( value = "/update/tournament/{tournamentId}/players", method = RequestMethod.POST)
    public ModelAndView addPlayer(@Valid@ModelAttribute("playerForm") PlayerForm playerForm, final BindingResult errors, @PathVariable long tournamentId, @ModelAttribute("loggedUser") User loggedUser){

        if (errors.hasErrors()){
            return tournament(playerForm, tournamentId);
        }

        final Tournament tournament = ts.findById(tournamentId);

        if (tournament == null) {
            return new ModelAndView("redirect:/404");
        }

        if (tournament.getCreator().getId() != loggedUser.getId()){
            LOGGER.warn("Unauthorized User {} tried to add player to tournament {}", loggedUser.getId(), tournamentId);
            return new ModelAndView("redirect:/403");
        }

        String username = playerForm.getUsername();
        final User user;
        final Player p;

        /* If username field is not empty */
        if (username != null) {
            user = us.findByName(playerForm.getUsername());

            if (!ts.participatesIn(user.getId(), tournamentId)) { /* user isn't participating */
                p = ps.create(playerForm.getPlayer(), user, tournament);
                ns.createParticipatesInNotifications(user, tournament);
            }else {
                errors.rejectValue("username", "playerForm.error.username.added");
                return tournament(playerForm, tournamentId);
            }

        }else{ /* Player is not linked to user */
            p = ps.create(playerForm.getPlayer(), tournament);
        }

        ps.addToTournament(p.getId(), tournamentId);

        return new ModelAndView("redirect:/tournament/"+ tournamentId +"/players");
    }

    @RequestMapping(value = "/update/{tournamentId}/{matchId}", method = { RequestMethod.POST })
    public ModelAndView updateMatch(@Valid @ModelAttribute("matchForm")
                               final MatchForm form, final BindingResult errors, @PathVariable long tournamentId, @PathVariable int matchId, @ModelAttribute("loggedUser") User loggedUser) {

        if (errors.hasErrors()) {
            return tournament(form,tournamentId);
        }

        final Tournament tournament = ts.findById(tournamentId);

        if (tournament == null) {
            return new ModelAndView("redirect:/404");
        }

        if (tournament.getCreator().getId() != loggedUser.getId()){
            LOGGER.warn("Unauthorized User {} tried to update match on tournament {}", loggedUser.getId(), tournamentId);
            return new ModelAndView("redirect:/403");
        }

        ms.updateScore(tournamentId,matchId,form.getHomeResult(),form.getAwayResult());
        LOGGER.info("Updated score of match {} from tournament {}", matchId, tournamentId);

        return new ModelAndView("redirect:/tournament/"+ tournamentId);
    }

    @RequestMapping(value = "/update/tournament/{tournamentId}/end", method = { RequestMethod.POST })
    public ModelAndView endTournament(@PathVariable long tournamentId, @ModelAttribute("loggedUser") User loggedUser) {

        final Tournament tournament = ts.findById(tournamentId);

        if (tournament == null) {
            return new ModelAndView("redirect:/404");
        }

        if (tournament.getCreator().getId() != loggedUser.getId()){
            LOGGER.warn("Unauthorized User {} tried to end the tournament {}", loggedUser.getId(), tournamentId);
            return new ModelAndView("redirect:/403");
        }

        ts.setStatus(tournamentId, Tournament.Status.FINISHED);
        LOGGER.info("Ended tournament {}", tournamentId);
        return new ModelAndView("redirect:/tournament/" + tournamentId);
    }

    @ResponseBody
    @RequestMapping(value="/gameImage/{gameId}", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] avatar(@PathVariable(value="gameId") final long gameId) {
        GameImage gameImage = gis.findById(gameId);
        if(gameImage == null){
            return null;
        } else {
            return gameImage.getImage();
        }
    }

    @RequestMapping(value = "/remove/player/{tournamentId}/{playerId}", method = { RequestMethod.POST })
    public ModelAndView removePlayer(@PathVariable long tournamentId, @PathVariable int playerId, @ModelAttribute("loggedUser") User loggedUser) {

        final Tournament tournament = ts.findById(tournamentId);

        if (tournament == null) {
            return new ModelAndView("redirect:/404");
        }

        if (tournament.getCreator().getId() != loggedUser.getId()){
            LOGGER.warn("Unauthorized User {} tried to remove player to tournament {}", loggedUser.getId(), tournamentId);
            return new ModelAndView("redirect:/403");
        }

        if(ps.removeFromTournament(tournamentId, playerId)) {
            ps.delete(playerId);
            LOGGER.info("Removed player {} from tournament {}", playerId, tournamentId);
        }
        return new ModelAndView("redirect:/tournament/"+ tournamentId + "/players");
    }

    @RequestMapping(value = "/swap/player/{tournamentId}/{playerOldSeed}/{playerNewSeed}", method = { RequestMethod.POST })
    public ModelAndView swapPlayer(@PathVariable long tournamentId, @PathVariable int playerOldSeed,@PathVariable int playerNewSeed, @ModelAttribute("loggedUser") User loggedUser) {

        final Tournament tournament = ts.findById(tournamentId);

        if (tournament == null) {
            return new ModelAndView("redirect:/404");
        }

        if (tournament.getCreator().getId() != loggedUser.getId()){
            LOGGER.warn("Unauthorized User {} tried to swap player to tournament {}", loggedUser.getId(), tournamentId);
            return new ModelAndView("redirect:/403");
        }

        ps.changeSeed(tournamentId, playerOldSeed, playerNewSeed);
        LOGGER.info("Swapped seed {} with seed {} from tournament {}", playerOldSeed, playerNewSeed, tournamentId);
        return new ModelAndView("redirect:/tournament/"+ tournamentId + "/players");
    }

    @RequestMapping(value ="/update/tournament/{tournamentId}/generate", method = {RequestMethod.POST})
    public ModelAndView generateBracket(@PathVariable long tournamentId, @ModelAttribute("loggedUser") User loggedUser){

        final Tournament tournament = ts.findById(tournamentId);

        if (tournament == null) {
            return new ModelAndView("redirect:/404");
        }

        if (tournament.getCreator().getId() != loggedUser.getId()){
            LOGGER.warn("Unauthorized User {} tried to generate the tournament {}", loggedUser.getId(), tournamentId);
            return new ModelAndView("redirect:/403");
        }

        ts.generateBracket(tournament);
        ts.setStatus(tournamentId, Tournament.Status.STARTED);
        LOGGER.debug("Generated bracket for tournament {}", tournamentId);
        return new ModelAndView("redirect:/tournament/" + tournamentId);
    }

    @RequestMapping("/tournament/{tournamentId}/comments")
    public ModelAndView comments(@PathVariable("tournamentId") long tournamentId, @ModelAttribute("commentForm") CommentForm form,
                                 @ModelAttribute("replyForm") ReplyForm replyForm){

        Tournament t = ts.findById(tournamentId);

        if (t == null){
            return new ModelAndView("redirect:/404");
        }

        LOGGER.debug("Access to tournament {} comments", t.getId());

        ModelAndView mav = new ModelAndView("tournament-comments");
        mav.addObject("tournament", t);
        return mav;
    }

    @RequestMapping(value = "/comment/tournament/{tournamentId}/", method = RequestMethod.POST)
    public ModelAndView addComment(@PathVariable("tournamentId") long tournamentId, @ModelAttribute("loggedUser") User loggedUser,
                                   @Valid @ModelAttribute("commentForm") final CommentForm form, final BindingResult errors){

        if (errors.hasErrors()){
            return comments(tournamentId, form, null);
        }

        Tournament t = ts.findById(tournamentId);

        if (t == null){
            return new ModelAndView("redirect:/404");
        }

        final Comment comment = cs.create(loggedUser, new Date(), form.getComment());
        ts.addComment(t.getId(), comment);

        return new ModelAndView("redirect:/tournament/" + tournamentId + "/comments");
    }

    @RequestMapping(value = "/reply/{parentId}/tournament/{tournamentId}", method = RequestMethod.POST)
    public ModelAndView replyComment(@PathVariable("tournamentId") long tournamentId, @PathVariable("parentId") long parentId, @ModelAttribute("loggedUser") User loggedUser,
                                   @Valid @ModelAttribute("replyForm") final ReplyForm form, final BindingResult errors){

        if (errors.hasErrors()){
            return comments(tournamentId, null, form);
        }

        Comment parent = cs.findById(parentId);

        Tournament t = ts.findById(tournamentId);

        if (t == null | parent == null){
            return new ModelAndView("redirect:/404");
        }

        final Comment reply = cs.create(loggedUser, new Date(), form.getReply(), parent);
        ts.addReply(t.getId(), reply, parentId);

        return new ModelAndView("redirect:/tournament/" + tournamentId + "/comments");
    }

    @RequestMapping(value = "/request/tournament/{tournamentId}", method = RequestMethod.POST)
    public ModelAndView requestJoin(@PathVariable("tournamentId") long tournamentId,
                                    @ModelAttribute("loggedUser") User loggedUser){

        Tournament t = ts.findById(tournamentId);

        if (t == null){
            return new ModelAndView("redirect:/404");
        }

        if (loggedUser == null){
            return new ModelAndView("redirect:/403");
        }

        if (is.findByIds(loggedUser.getId(), t.getId()) == null) {
            is.create(loggedUser, t);
        }else{
            return new ModelAndView("redirect:/404");
        }

        return new ModelAndView("redirect:/tournament/" + tournamentId + "/players");
    }

    @RequestMapping(value = "/accept/user/{userId}/tournament/{tournamentId}", method = RequestMethod.POST)
    public ModelAndView acceptJoinRequest(@PathVariable long userId, @PathVariable long tournamentId, @ModelAttribute("loggedUser") User loggedUser){

        final Tournament t = ts.findById(tournamentId);

        final User u = us.findById(userId);

        if (t == null || u == null){
            return new ModelAndView("redirect:/404");
        }

        /* remove from pending list  */
        is.delete(tournamentId, userId);

        Player p;

        if (!ts.participatesIn(u.getId(), tournamentId)) { /* user isn't participating */
            p = ps.create(u.getName(), u, t);
        }else{
            return new ModelAndView("redirect:/tournament/" + tournamentId + "/players");
        }

        ps.addToTournament(p.getId(), t.getId());

        return new ModelAndView("redirect:/tournament/" + tournamentId + "/players");
    }

    @RequestMapping(value = "/reject/user/{userId}/tournament/{tournamentId}", method = RequestMethod.POST)
    public ModelAndView rejectJoinRequest(@PathVariable long userId, @PathVariable long tournamentId, @ModelAttribute("loggedUser") User loggedUser){

        final Tournament t = ts.findById(tournamentId);

        final User u = us.findById(userId);

        if (t == null || u == null){
            return new ModelAndView("redirect:/404");
        }

        /* remove from pending list  */
        is.delete(tournamentId, userId);

        return new ModelAndView("redirect:/tournament/" + tournamentId + "/players");
    }

    /*
       Empty strings as null for optional fields
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
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
