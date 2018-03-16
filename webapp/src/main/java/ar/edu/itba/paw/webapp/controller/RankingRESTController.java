package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.RankingService;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.webapp.controller.dto.RankingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("rankings")
@Component
public class RankingRESTController {

  @Autowired
  private RankingService rs;

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
}
