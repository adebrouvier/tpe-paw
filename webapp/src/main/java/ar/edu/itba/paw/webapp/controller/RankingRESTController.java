package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.controller.dto.RankingDTO;
import ar.edu.itba.paw.webapp.form.RankingForm;
import ar.edu.itba.paw.webapp.form.TournamentForm;
import ar.edu.itba.paw.webapp.form.validation.RESTValidator;
import ar.edu.itba.paw.webapp.form.validation.ValidationException;
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
import java.util.HashMap;
import java.util.Map;

@Path("rankings")
@Component
public class RankingRESTController {

  @Autowired
  private RankingService rs;

  @Context
  private UriInfo uriInfo;

  @Autowired
  private RESTValidator validator;

  private static final Logger LOGGER = LoggerFactory.getLogger(RankingRESTController.class);

  @GET
  @Path("/{id}")
  @Produces(value = { MediaType.APPLICATION_JSON, })
  public Response getById(@PathParam("id") final long id) {
    final Ranking ranking = rs.findById(id);
    if (ranking != null) {
      return Response.ok(new RankingDTO(ranking)).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  @POST
  @Path("/")
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public	Response	createRanking(final RankingForm rankingForm) throws ValidationException {

    validator.validate(rankingForm, "Failed to validate ranking");

    //TODO: constructor without tMap?
    Map<Tournament, Integer> tMap = new HashMap<>();
    final Ranking ranking	=	rs.create(rankingForm.getName(), tMap, rankingForm.getName(), 1);

    LOGGER.info("Created ranking {} with id {}", ranking.getName(), ranking.getId());

    final URI uri	=	uriInfo.getAbsolutePathBuilder().path(String.valueOf(ranking.getId())).build();
    return	Response.created(uri).build();
  }
}
