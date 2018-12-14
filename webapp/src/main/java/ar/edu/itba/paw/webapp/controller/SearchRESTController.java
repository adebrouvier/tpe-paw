package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.webapp.controller.dto.RankingDTO;
import ar.edu.itba.paw.webapp.controller.dto.TournamentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Path("/search")
@Component
public class SearchRESTController {

  @Autowired
  private TournamentService ts;

  @Autowired
  private RankingService rs;

  @GET
  @Path("/tournaments")
  @Produces(value = {MediaType.APPLICATION_JSON,})
  public Response searchTournaments(@QueryParam("q") String query, @QueryParam("game") String game) {
    final List<Tournament> tournament = ts.findByName(query, game);

    if (tournament != null) {
      final List<TournamentDTO> tournaments = tournament.stream()
        .map(TournamentDTO::new)
        .collect(Collectors.toList());
      GenericEntity<List<TournamentDTO>> list = new GenericEntity<List<TournamentDTO>>(tournaments) {
      };
      return Response.ok(list).build();
    } else {
      final List<TournamentDTO> tournaments = new ArrayList<>();
      GenericEntity<List<TournamentDTO>> emptyList = new GenericEntity<List<TournamentDTO>>(tournaments) {
      };
      return Response.ok(emptyList).build();
    }
  }

  @GET
  @Path("/rankings")
  @Produces(value = {MediaType.APPLICATION_JSON,})
  public Response searchRankings(@QueryParam("q") String query, @QueryParam("game") String game) {
    final List<Ranking> rankings = rs.findByName(query, game);

    if (rankings != null) {
      final List<RankingDTO> rankingDTO = rankings.stream()
        .map(RankingDTO::new)
        .collect(Collectors.toList());
      GenericEntity<List<RankingDTO>> list = new GenericEntity<List<RankingDTO>>(rankingDTO) {
      };
      return Response.ok(list).build();
    } else {
      final List<TournamentDTO> tournaments = new ArrayList<>();
      GenericEntity<List<TournamentDTO>> emptyList = new GenericEntity<List<TournamentDTO>>(tournaments) {
      };
      return Response.ok(emptyList).build();
    }
  }

}
