package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.Comment;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.controller.dto.CommentDTO;
import ar.edu.itba.paw.webapp.controller.dto.PlayerDTO;
import ar.edu.itba.paw.webapp.controller.dto.TournamentDTO;
import ar.edu.itba.paw.webapp.form.CommentForm;
import ar.edu.itba.paw.webapp.form.PlayerForm;
import ar.edu.itba.paw.webapp.form.ReplyForm;
import ar.edu.itba.paw.webapp.form.TournamentForm;
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

    return	Response.ok(Response.Status.CREATED).build();
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

    return	Response.ok().build();
  }
}
