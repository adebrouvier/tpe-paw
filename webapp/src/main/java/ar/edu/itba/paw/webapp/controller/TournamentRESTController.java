package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.controller.dto.TournamentDTO;
import ar.edu.itba.paw.webapp.form.TournamentForm;
import ar.edu.itba.paw.webapp.form.validation.ValidationException;
import ar.edu.itba.paw.webapp.form.validation.RESTValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
  @Path("/")
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public	Response	createTournament(final TournamentForm tournamentForm) throws ValidationException {

    validator.validate(tournamentForm, "Failed to validate tournament");

    //TODO: parse game
    final Tournament tournament	=	ts.create(tournamentForm.getName(), 1, 1);

    LOGGER.info("Created tournament {} with id {}", tournament.getName(), tournament.getId());

    final URI uri	=	uriInfo.getAbsolutePathBuilder().path(String.valueOf(tournament.getId())).build();
    return	Response.created(uri).build();
  }
}
