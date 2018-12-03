package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.SecurityService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.TournamentPoints;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.controller.dto.RankingDTO;
import ar.edu.itba.paw.webapp.form.RankingForm;
import ar.edu.itba.paw.webapp.form.RankingPageForm;
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

  @Autowired
  private TournamentService ts;

  @Autowired
  private PlayerMeController pmc;

  @Autowired
  private NotificationService ns;

  @Autowired
  private SecurityService ss;

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
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public Response createRanking(final RankingForm rankingForm) throws ValidationException {

    validator.validate(rankingForm, "Failed to validate ranking");

    final User loggedUser = ss.getLoggedUser();

    pmc.addGameImage(rankingForm.getGame());

    //TODO: constructor without tMap?
    Map<Tournament, Integer> tMap = new HashMap<>();
    final Ranking ranking	=	rs.create(rankingForm.getName(), tMap, rankingForm.getName(), loggedUser.getId());

    LOGGER.info("Created ranking {} with id {}", ranking.getName(), ranking.getId());

    final URI uri	=	uriInfo.getAbsolutePathBuilder().path(String.valueOf(ranking.getId())).build();
    return	Response.created(uri).build();
  }

  @POST
  @Path("/{id}/tournaments")
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public Response addTournament(@PathParam("id") final long id, final RankingPageForm form) throws ValidationException {

    validator.validate(form, "Failed to validate form");

    final Ranking ranking = rs.findById(id);

    if (ranking == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    final User loggedUser = ss.getLoggedUser();

    if (!loggedUser.equals(ranking.getUser())) {
      LOGGER.warn("Unauthorized User {} tried to add tournament to ranking {}", loggedUser.getId(), id);
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    Map<Tournament, Integer> tMap = new HashMap<>();
    final Tournament tournament = ts.getByNameAndGameId(form.getTournamentName(), ranking.getGame().getId());

    if (tournament == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else if (tournament.getGame().getId() != ranking.getGame().getId()) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else if (tournament.getStatus() != Tournament.Status.FINISHED) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else {
      for (TournamentPoints tPoints : ranking.getTournaments()) {
        if (tournament.getId() == tPoints.getTournament().getId()) { // Duplicated tournament
          return Response.status(Response.Status.NOT_FOUND).build();
        }
      }
    }
    tMap.put(tournament, form.getPoints());
    rs.addTournaments(id, tMap);
    LOGGER.info("Added tournament {} to ranking {}", tournament.getId(), id);

    ns.createAddTournamentToRankingNotification(loggedUser, tournament, ranking);

    return Response.ok().build();
  }

  @DELETE
  @Path("/{id}/tournaments/{tournamentId}")
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public Response removeTournament(@PathParam("id") final long id, @PathParam("tournamentId") final long tournamentId,
                                   final RankingPageForm form){

    final Ranking r = rs.findById(id);

    if (r == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    final User loggedUser = ss.getLoggedUser();
    if (!loggedUser.equals(r.getUser())) {
      LOGGER.warn("Unauthorized User {} tried to remove tournament to ranking {}", loggedUser.getId(), id);
      return Response.status(Response.Status.UNAUTHORIZED).build();
    }
    rs.delete(id, tournamentId);
    LOGGER.info("Deleted tournament {} from ranking {}", tournamentId, id);

    return Response.ok().build();
  }
}
