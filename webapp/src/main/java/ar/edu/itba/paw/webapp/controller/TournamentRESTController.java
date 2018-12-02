package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.webapp.controller.dto.*;
import ar.edu.itba.paw.webapp.form.*;
import ar.edu.itba.paw.webapp.form.validation.RESTValidator;
import ar.edu.itba.paw.webapp.form.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Path("tournaments")
@Component
public class TournamentRESTController {

  @Autowired
  private TournamentService ts;

  @Autowired
  private UserService us;

  @Autowired
  private SecurityService ss;

  @Autowired
  private PlayerService ps;

  @Autowired
  private NotificationService ns;

  @Autowired
  private CommentService cs;

  @Autowired
  private MatchService ms;

  @Autowired
  private InscriptionService is;

  @Context
  private UriInfo uriInfo;

  @Autowired
  private RESTValidator validator;

  private static final Logger LOGGER = LoggerFactory.getLogger(TournamentRESTController.class);

  @GET
  @Path("/{id}")
  @Produces(value = { MediaType.APPLICATION_JSON, })
  public Response getById(@PathParam("id") final long id) {
    final Tournament tournament = ts.findById(id);
    if (tournament != null) {
      return Response.ok(new TournamentDTO(tournament)).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @POST
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public	Response	createTournament(final TournamentForm tournamentForm) throws ValidationException {

    validator.validate(tournamentForm, "Failed to validate tournament");

    User loggedUser = ss.getLoggedUser();

    if (loggedUser == null){
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    //TODO: parse game
    final Tournament tournament	=	ts.create(tournamentForm.getName(), 1, loggedUser.getId());

    LOGGER.info("Created tournament {} with id {}", tournament.getName(), tournament.getId());

    final URI uri	=	uriInfo.getAbsolutePathBuilder().path(String.valueOf(tournament.getId())).build();
    return	Response.created(uri).entity(new TournamentDTO(tournament)).build();
  }

  @POST
  @Path("/{id}/players")
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public Response players(@PathParam("id") final long id, final PlayerForm playerForm) throws ValidationException {

    validator.validate(playerForm, "Failed to validate player");

    final Tournament tournament = ts.findById(id);

    if (tournament == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();

    if (!loggedUser.equals(tournament.getCreator())){
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    String username = playerForm.getUsername();
    final User user;
    Player p = null;

    // If username field is not empty
    if (username != null) {
      user = us.findByName(playerForm.getUsername());

      if (!ts.participatesIn(user.getId(), id)) { // user isn't participating
        p = ps.create(playerForm.getPlayer(), user, tournament);
        ns.createParticipatesInNotifications(user, tournament);
      }

    } else { // Player is not linked to user
      p = ps.create(playerForm.getPlayer(), tournament);
    }

    ps.addToTournament(p.getId(), id);
    LOGGER.info("Added player {} to tournament {}", p.getName(), id);

    /* TODO: Change for service? */
    List<PlayerDTO> players = ts.findById(id).getPlayers().stream()
      .map(PlayerDTO::new)
      .collect(Collectors.toList());

    GenericEntity<List<PlayerDTO>> playerList = new GenericEntity<List<PlayerDTO>>(players) { };
    return	Response.ok(playerList).build();
  }

  /*TODO: change to DELETE */
  @POST
  @Path("/{id}/players/{playerId}")
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public Response removePlayer(@PathParam("id") final long id, @PathParam("playerId") final long playerId) {

    final Tournament tournament = ts.findById(id);

    if (tournament == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();

    if (!loggedUser.equals(tournament.getCreator())){
      LOGGER.warn("Unauthorized User {} tried to remove player to tournament {}", loggedUser.getId(), id);
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    if (ps.removeFromTournament(id, playerId)) {
      ps.delete(playerId);
      LOGGER.info("Removed player {} from tournament {}", playerId, id);
    }

    /* TODO: Change for service? */
    List<PlayerDTO> players = ts.findById(id).getPlayers().stream()
                                .map(PlayerDTO::new)
                                .collect(Collectors.toList());

    GenericEntity<List<PlayerDTO>> playerList = new GenericEntity<List<PlayerDTO>>(players) { };
    return	Response.ok(playerList).build();
  }

  @POST
  @Path("/{id}/generate")
  public Response generateBracket(@PathParam("id") final long id) {

    final Tournament tournament = ts.findById(id);

    if (tournament == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();

    if (!loggedUser.equals(tournament.getCreator())){
      LOGGER.warn("Unauthorized User {} tried to generate the tournament {}", loggedUser.getId(), id);
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    ts.generateBracket(tournament);
    ts.setStatus(id, Tournament.Status.STARTED);
    LOGGER.debug("Generated bracket for tournament {}", id);

    return	Response.ok().build();
  }

  @POST
  @Path("{id}/matches/{matchId}")
  public Response updateMatchScore(@PathParam("id") final long id, @PathParam("matchId") final int matchId, final MatchForm matchForm) throws ValidationException {

    validator.validate(matchForm, "Failed to validate match");

    final Tournament tournament = ts.findById(id);

    if (tournament == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();

    if (!tournament.getCreator().equals(loggedUser)) {
      LOGGER.warn("Unauthorized User {} tried to update match on tournament {}", loggedUser.getId(), id);
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    Match m = ms.updateScore(id, matchId, matchForm.getHomeResult(), matchForm.getAwayResult());

    if (m == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    LOGGER.info("Updated score of match {} from tournament {}", matchId, id);

    List<MatchDTO> matches = ts.findById(id).getMatches().stream()
                              .map(MatchDTO::new)
                              .collect(Collectors.toList());

    GenericEntity<List<MatchDTO>> matchList = new GenericEntity<List<MatchDTO>>(matches) { };
    return	Response.ok(matchList).build();
  }

  @GET
  @Path("/{id}/comments")
  public Response comments(@PathParam("id") final long id) {

    final Tournament tournament = ts.findById(id);

    if (tournament == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    List<CommentDTO> comments = ts.findById(id).getComments().stream()
      .map(CommentDTO::new)
      .collect(Collectors.toList());

    GenericEntity<List<CommentDTO>> commentList = new GenericEntity<List<CommentDTO>>(comments) { };
    return	Response.ok(commentList).build();
  }

  @POST
  @Path("/{id}/comments")
  public Response addComment(@PathParam("id") final long id, CommentForm commentForm) throws ValidationException {

    validator.validate(commentForm, "Failed to validate comment");

    final Tournament tournament = ts.findById(id);

    if (tournament == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();

    final Comment comment = cs.create(loggedUser, new Date(), commentForm.getComment());
    ts.addComment(id, comment);

    return	Response.ok(new CommentDTO(comment)).build();
  }

  @POST
  @Path("/{id}/comments/{commentId}")
  public Response replyComment(@PathParam("id") final long id, @PathParam("commentId") final long commentId, ReplyForm replyForm) throws ValidationException {

    validator.validate(replyForm, "Failed to validate reply");

    final Tournament t = ts.findById(id);
    final Comment parent = cs.findById(commentId);

    if (t == null || parent == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();

    final Comment reply = cs.create(loggedUser, new Date(), replyForm.getReply(), parent);
    ts.addReply(t.getId(), reply, parent.getId());

    if (parent.getCreator().getId() != loggedUser.getId()) {
      ns.createReplyTournamentCommentsNotification(loggedUser, parent, t);
      LOGGER.info("New reply on tournament {}", t.getId());
    }

    return	Response.ok(new CommentDTO(reply)).build();
  }

  @POST
  @Path("/{id}/end")
  public Response endTournament(@PathParam("id") final long id){

    final Tournament tournament = ts.findById(id);

    if (tournament == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();

    if (!tournament.getCreator().equals(loggedUser)) {
      LOGGER.warn("Unauthorized User {} tried to end the tournament {}", loggedUser.getId(), id);
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    ts.setStatus(id, Tournament.Status.FINISHED);
    LOGGER.info("Ended tournament {}", id);

    return	Response.ok().build();
  }

  @GET
  @Path("/{id}/inscriptions")
  public Response inscriptions(@PathParam("id") final long id){

    final Tournament tournament = ts.findById(id);

    if (tournament == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    final List<UserDTO> inscriptions = is.finByTournamentId(id).stream()
                    .map(Inscription::getUser)
                    .map(UserDTO::new)
                    .collect(Collectors.toList());

    GenericEntity<List<UserDTO>> inscriptionList = new GenericEntity<List<UserDTO>>(inscriptions) { };
    return	Response.ok(inscriptionList).build();
  }

  @POST
  @Path("/{id}/inscriptions")
  public Response requestInscription(@PathParam("id") final long id){

    Tournament tournament = ts.findById(id);

    if (tournament == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();

    if (loggedUser.equals(tournament.getCreator())){
      return Response.status(Response.Status.FORBIDDEN).build();
    }

    if (is.findByIds(loggedUser.getId(), tournament.getId()) == null) {
      is.create(loggedUser, tournament);
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    ns.createRequestJoinNotification(loggedUser, tournament);
    LOGGER.info("User {} created join request on tournament {}", loggedUser.getId(), id);

    final List<UserDTO> inscriptions = is.finByTournamentId(id).stream()
      .map(Inscription::getUser)
      .map(UserDTO::new)
      .collect(Collectors.toList());

    GenericEntity<List<UserDTO>> inscriptionList = new GenericEntity<List<UserDTO>>(inscriptions) { };
    return	Response.ok(inscriptionList).build();
  }

  @POST
  @Path("/{id}/inscriptions/{userId}")
  public Response acceptInscription(@PathParam("id") final long id, @PathParam("userId") final long userId){

    final Tournament t = ts.findById(id);

    final User u = us.findById(userId);

    if (t == null || u == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();
    if (!loggedUser.equals(t.getCreator())){
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /* remove from pending list  */
    is.delete(id, userId);

    Player p;

    if (!ts.participatesIn(u.getId(), id)) { /* user isn't participating */
      p = ps.create(u.getName(), u, t);
    } else {
      return	Response.ok().build();
    }

    ps.addToTournament(p.getId(), t.getId());

    ns.createAcceptJoinNotification(u, t);

    LOGGER.info("Accepted user {} join request on tournament {}", userId, id);

    final List<UserDTO> inscriptions = is.finByTournamentId(id).stream()
      .map(Inscription::getUser)
      .map(UserDTO::new)
      .collect(Collectors.toList());

    GenericEntity<List<UserDTO>> inscriptionList = new GenericEntity<List<UserDTO>>(inscriptions) { };
    return	Response.ok(inscriptionList).build();
  }

  @DELETE
  @Path("/{id}/inscriptions/{userId}")
  public Response rejectInscription(@PathParam("id") final long id, @PathParam("userId") final long userId){

    final Tournament t = ts.findById(id);

    final User u = us.findById(userId);

    if (t == null || u == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();
    if (!loggedUser.equals(t.getCreator())){
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    /* remove from pending list  */
    is.delete(id, userId);
    ns.createRejectJoinNotification(u, t);

    LOGGER.info("Rejected user {} join request on tournament {}", userId, id);

    final List<UserDTO> inscriptions = is.finByTournamentId(id).stream()
      .map(Inscription::getUser)
      .map(UserDTO::new)
      .collect(Collectors.toList());

    GenericEntity<List<UserDTO>> inscriptionList = new GenericEntity<List<UserDTO>>(inscriptions) { };
    return	Response.ok(inscriptionList).build();
  }

  @GET
  @Path("/{id}/inscriptions/{userId}")
  @Produces(MediaType.TEXT_PLAIN)
  public Boolean signedUp(@PathParam("id") final long id, @PathParam("userId") final long userId){

    final Tournament t = ts.findById(id);

    final User u = us.findById(userId);

    if (t == null || u == null) {
      return false;
    }

    return is.findByIds(userId, id) != null;
  }

  @GET
  @Path("/{id}/participates/{userId}")
  @Produces(MediaType.TEXT_PLAIN)
  public Boolean participates(@PathParam("id") final long id, @PathParam("userId") final long userId){

    final Tournament t = ts.findById(id);

    final User u = us.findById(userId);

    if (t == null || u == null) {
      return false;
    }

    return ts.participatesIn(u.getId(), id);
  }

  @GET
  @Path("/{id}/match/{matchId}")
  public Response getMatchById(@PathParam("id") final long id, @PathParam("matchId") final int matchId) {
    final Match match = ms.findById(matchId, id);
    if (match != null) {
      return Response.ok(new MatchDTO(match)).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @POST
  @Path("/{id}/match/{matchId}/details")
  public Response updateMatch(@PathParam("id") final long id, @PathParam("matchId") final int matchId, MatchDataForm form){

    final Match match = ms.findById(matchId, id);

    if (match == null){
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    User loggedUser = ss.getLoggedUser();
    if (!loggedUser.equals(match.getTournament().getCreator())){
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    if (form.getHomePlayerCharacter() != null)
      ms.setHomePlayerCharacter(form.getHomePlayerCharacter(), id, matchId);
    if (form.getAwayPlayerCharacter() != null)
      ms.setAwayPlayerCharacter(form.getAwayPlayerCharacter(), id, matchId);
    if (form.getMap() != null) ms.setMap(form.getMap(), id, matchId);
    if (form.getVodLink() != null) ms.setVODLink(form.getVodLink(), id, matchId);

    LOGGER.info("Update match {} from tournament {} description", matchId, id);

    return	Response.ok().build();
  }

}
