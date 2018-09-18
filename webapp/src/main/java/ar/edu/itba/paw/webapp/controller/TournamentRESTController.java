package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.PlayerService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.controller.dto.TournamentDTO;
import ar.edu.itba.paw.webapp.form.PlayerForm;
import ar.edu.itba.paw.webapp.form.TournamentForm;
import ar.edu.itba.paw.webapp.form.validation.ValidationException;
import ar.edu.itba.paw.webapp.form.validation.RESTValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("tournaments")
@Component
public class TournamentRESTController {

  @Autowired
  private TournamentService ts;

  @Autowired
  private UserService us;

  @Autowired
  private PlayerService ps;

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

    //TODO: parse game
    final Tournament tournament	=	ts.create(tournamentForm.getName(), 1, 1);

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

    //TODO: 403

    /*String username = playerForm.getUsername();
    final User user;
    final Player p;

    *//* If username field is not empty *//*
    if (username != null) {
      user = us.findByName(playerForm.getUsername());

      if (!ts.participatesIn(user.getId(), id)) { *//* user isn't participating *//*
        p = ps.create(playerForm.getPlayer(), user, tournament);
        //TODO: ns.createParticipatesInNotifications(user, tournament);
      } else {
        errors.rejectValue("username", "playerForm.error.username.added");
        return tournament(playerForm, tournamentId, loggedUser);
      }

    } else { *//* Player is not linked to user *//*
      p = ps.create(playerForm.getPlayer(), tournament);
    }

    ps.addToTournament(p.getId(), id);*/

    return	Response.ok().build();
  }

  @ModelAttribute("loggedUser")
  public User loggedUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    String username;
    if (principal instanceof UserDetails) {
      username = ((UserDetails) principal).getUsername();
    } else {
      username = principal.toString();
    }
    return us.findByName(username);
  }
}
