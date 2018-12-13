package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.SecurityService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.webapp.controller.dto.*;
import ar.edu.itba.paw.webapp.form.RankingForm;
import ar.edu.itba.paw.webapp.form.RankingPageForm;
import ar.edu.itba.paw.webapp.form.validation.RESTValidator;
import ar.edu.itba.paw.webapp.form.validation.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    Game game = pmc.addGameImage(rankingForm.getGame());

    //TODO: constructor without tMap?
    Map<Tournament, Integer> tMap = new HashMap<>();
    final Ranking ranking	=	rs.create(rankingForm.getName(), tMap, game.getName(), loggedUser.getId());

    LOGGER.info("Created ranking {} with id {}", ranking.getName(), ranking.getId());

    final URI uri	=	uriInfo.getAbsolutePathBuilder().path(String.valueOf(ranking.getId())).build();
    return	Response.created(uri).entity(new RankingDTO(ranking)).build();
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

    final List<TournamentPointsDTO> points = rs.findById(id).getTournaments().stream()
      .map(TournamentPointsDTO::new)
      .collect(Collectors.toList());

    GenericEntity<List<TournamentPointsDTO>> pointsList = new GenericEntity<List<TournamentPointsDTO>>(points) { };
    return	Response.ok(pointsList).build();
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

    final List<TournamentPointsDTO> points = rs.findById(id).getTournaments().stream()
      .map(TournamentPointsDTO::new)
      .collect(Collectors.toList());

    GenericEntity<List<TournamentPointsDTO>> pointsList = new GenericEntity<List<TournamentPointsDTO>>(points) { };
    return	Response.ok(pointsList).build();
  }

  @GET
  @Path("{id}/tournament/{tournament}")
  public Response validTournament(@PathParam("id") final long id, @PathParam("tournament") String name){

    boolean valid = rs.checkValidTournament(id, name);

    return Response.ok(new ValidDTO(valid)).build();
  }


  @GET
  @Path("/featured")
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public Response featuredRankings() {

    int amount = 5;
    List<RankingDTO> featured = rs.findFeaturedRankings(amount).stream()
      .map(RankingDTO::new)
      .collect(Collectors.toList());

    GenericEntity<List<RankingDTO>> list= new GenericEntity<List<RankingDTO>>(featured) { };
    return Response.ok(list).build();
  }

  @GET
  @Path("/popular")
  @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
  public Response popularRankings() {

    int amount = 5;
    List<PopularRankingRESTDTO> popular = rs.findPopularRankings(amount).stream()
                                                                        .map(PopularRankingRESTDTO::new)
                                                                        .collect(Collectors.toList());

    GenericEntity<List<PopularRankingRESTDTO>> list= new GenericEntity<List<PopularRankingRESTDTO>>(popular) { };
    return Response.ok(list).build();
  }
}
