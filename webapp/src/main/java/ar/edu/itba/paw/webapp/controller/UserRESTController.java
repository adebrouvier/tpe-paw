package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserImage;
import ar.edu.itba.paw.webapp.controller.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.List;

@Path("users")
@Component
public class UserRESTController {

    @Autowired
    private UserService us;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private UserImageService uis;

    @Autowired
    private ApplicationContext appContext;

    @Autowired
    private TournamentService ts;

    @Autowired
    private RankingService rs;

    @Autowired
    private UserFollowService ufs;

    @Autowired
    private UserFavoriteGameService ufgs;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRESTController.class);

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("id") final long id) {
        final User user = us.findById(id);
        if (user != null) {
            return Response.ok(new UserDTO(user,ufgs.getFavoriteGames(user),us.getFollowersAmount(id),ts.findTournamentByParticipant(id, 0),ts.findTournamentByUser(id, 0),rs.findRankingByUserPage(id, 0))).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/profile-image/{id}")
    @Produces(value = {MediaType.MULTIPART_FORM_DATA})
    public byte[] avatar(@PathParam("id") final long id) throws IOException {

      UserImage ui = uis.findById(id);
      if (ui != null) {
        return ui.getImage();
      } else {
        Resource r = appContext.getResource("/resources/img/user-profile-image.jpg");
        long l = r.contentLength();
        byte[] ans = new byte[(int) l];
        r.getInputStream().read(ans);
        return ans;
      }
    }

    @GET
    @Path("/{id}/participated-tournaments")
    @Produces(value = { MediaType.APPLICATION_JSON})
    @ResponseBody
    public Response getParticipatedTournaments(@PathParam("id") final long id,@QueryParam("page") final int page) {
      final User user = us.findById(id);
      if (user != null) {
        List<Tournament> tournaments = ts.findTournamentByParticipant(id, page);
        UserDTO userDTO = new UserDTO(null,null,null,tournaments,null,null);
        return Response.ok(userDTO).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    }

    @GET
    @Path("/{id}/created-tournaments")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getCreatedTournaments(@PathParam("id") final long id, @QueryParam("page") final int page) {
      final User user = us.findById(id);
      if (user != null) {
        List<Tournament> tournaments = ts.findTournamentByUser(id, page);
        UserDTO userDTO = new UserDTO(null,null,null,null,tournaments,null);
        return Response.ok(userDTO).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    }

    @GET
    @Path("/{id}/created-rankings")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getCreatedRankings(@PathParam("id") final long id, @QueryParam("page") final int page) {
      final User user = us.findById(id);
      if (user != null) {
        List<Ranking> rankings = rs.findRankingByUserPage(id, page);
        UserDTO userDTO = new UserDTO(null,null,null,null,null,rankings);
        return Response.ok(userDTO).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    }

    /*@POST
    @Path("/")
    @Produces(value	=	{	MediaType.APPLICATION_JSON,	})
    public	Response	createUser(final	UserDTO	userDto)	{
      final	User	user	=	us.create(userDto.getUsername(),	passwordEncoder.encode(userDto.getPassword()));

      LOGGER.info("Registered user {} with id {}", user.getName(), user.getId());

      final URI uri	=	uriInfo.getAbsolutePathBuilder().path(String.valueOf(user.getId())).build();
      return	Response.created(uri).build();
    }*/
}
