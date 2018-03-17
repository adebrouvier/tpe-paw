package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.controller.dto.TournamentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

@Path("tournaments")
@Component
public class TournamentRESTController {

  @Autowired
  private TournamentService ts;

  @Context
  private UriInfo uriInfo;

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

  /*@POST
  @Path("/")
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public	Response	createTournament(final TournamentDTO tournamentDTO) {
    final Tournament tournament	=	ts.create(tournamentDTO.getName(), tournamentDTO.getGame().getId(), 1);

    LOGGER.info("Created tournament {} with id {}", tournament.getName(), tournament.getId());

    final URI uri	=	uriInfo.getAbsolutePathBuilder().path(String.valueOf(tournament.getId())).build();
    return	Response.created(uri).build();
  }*/
}
