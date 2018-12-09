package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.*;
import ar.edu.itba.paw.model.*;
import ar.edu.itba.paw.webapp.controller.dto.UserDTO;
import ar.edu.itba.paw.webapp.controller.dto.UserPictureDto;
import ar.edu.itba.paw.webapp.form.UserUpdateForm;
import ar.edu.itba.paw.webapp.form.validation.RESTValidator;
import ar.edu.itba.paw.webapp.form.validation.ValidationException;
import org.glassfish.jersey.media.multipart.FormDataParam;
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
import java.net.URI;
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
    private GameService gs;

    @Autowired
    private PlayerMeController pmc;

    @Autowired
    private UserFavoriteGameService ufgs;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private SecurityService ss;

    @Autowired
    private RESTValidator validator;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRESTController.class);

    @GET
    @Path("/{username}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("username") final String username) {
        final User user = us.findByName(username);

        if (user != null) {
            return Response.ok(new UserDTO(user,ufs.isFollow(ss.getLoggedUser(),user),ufgs.getFavoriteGames(user),us.getFollowersAmount(user.getId()),ts.findTournamentByParticipant(user.getId(), 0),ts.findTournamentByUser(user.getId(), 0),rs.findRankingByUserPage(user.getId(), 0))).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/profile-image/{username}")
    @Produces(value = {MediaType.MULTIPART_FORM_DATA})
    public byte[] avatar(@PathParam("username") final String username) throws IOException {
      User u = us.findByName(username);
      if(u != null) {
        UserImage ui = uis.findById(u.getId());
        if (ui != null) {
          return ui.getImage();
        } else {
          Resource r = appContext.getResource("/resources/img/user-profile-image.jpg");
          long l = r.contentLength();
          byte[] ans = new byte[(int) l];
          r.getInputStream().read(ans);
          return ans;
        }
      } else {
        return null;
      }

    }

    @GET
    @Path("/{username}/participated-tournaments")
    @Produces(value = { MediaType.APPLICATION_JSON})
    @ResponseBody
    public Response getParticipatedTournaments(@PathParam("username") final String username,@QueryParam("page") final int page) {
      final User user = us.findByName(username);
      if (user != null) {
        List<Tournament> tournaments = ts.findTournamentByParticipant(user.getId(), page);
        UserDTO userDTO = new UserDTO(null,false,null,null,tournaments,null,null);
        return Response.ok(userDTO).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    }

  @POST
  @Path("/{username}/follow")
  @Produces(value = { MediaType.APPLICATION_JSON, })
  public final Response followUser(@PathParam("username") String username) {
    User u = us.findByName(username);
    User loggedUser = ss.getLoggedUser();
    if (u == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    if (loggedUser == null || u.getId() == loggedUser.getId()) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }

    ufs.create(loggedUser, u);
    LOGGER.debug("New follower for user {}", u.getId());

    return Response.status(Response.Status.OK).build();
  }

  @POST
  @Path("/{username}/unfollow")
  @Produces(value = { MediaType.APPLICATION_JSON, })
  public final Response unfollowUser(@PathParam("username") String username) {
    User u = us.findByName(username);

    User loggedUser = ss.getLoggedUser();

    if (u == null) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    if (loggedUser == null || u.getId() == loggedUser.getId()) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }

    ufs.delete(loggedUser, u);
    LOGGER.debug("Unfollow for user {}", u.getId());

    return Response.status(Response.Status.OK).build();
  }


    @GET
    @Path("/{username}/created-tournaments")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getCreatedTournaments(@PathParam("username") final String username, @QueryParam("page") final int page) {
      final User user = us.findByName(username);
      if (user != null) {
        List<Tournament> tournaments = ts.findTournamentByUser(user.getId(), page);
        UserDTO userDTO = new UserDTO(null,false,null,null,null,tournaments,null);
        return Response.ok(userDTO).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    }

    @GET
    @Path("/{username}/created-rankings")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getCreatedRankings(@PathParam("username") final String username, @QueryParam("page") final int page) {
      final User user = us.findByName(username);
      if (user != null) {
        List<Ranking> rankings = rs.findRankingByUserPage(user.getId(), page);
        UserDTO userDTO = new UserDTO(null,false,null,null,null,null,rankings);
        return Response.ok(userDTO).build();
      } else {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
    }

    @PUT
    @Path("/{username}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value	=	{MediaType.MULTIPART_FORM_DATA})
    public Response updateUser(@PathParam("username") final String username, @FormDataParam("user") final UserUpdateForm userForm, @BeanParam final UserPictureDto userPictureDto) throws ValidationException {

      User loggedUser = ss.getLoggedUser();
      final User u = us.findByName(username);
      if(u == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      if(loggedUser.getId() != u.getId()) {
        return Response.status(Response.Status.FORBIDDEN).build();
      }

      validator.validate(userForm, "Failed to validate tournament");

      if (userForm == null) {
        return Response.status(Response.Status.BAD_REQUEST).build();
      }

      Game g = gs.findByName(userForm.getGame());
      if (g == null) {
        g = pmc.addGameImage(userForm.getGame());
      }
      ufgs.deleteAll(u);
      ufgs.create(u, g);
      us.updateDescription(u, userForm.getDescription(), userForm.getTwitchUrl(), userForm.getTwitterUrl(), userForm.getYoutubeUrl());
      if(userPictureDto != null) {
        uis.updateImage(u, userPictureDto.getImage().getValueAs(byte[].class));
      }
      final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(u.getId())).build();

      return Response.created(location).build();

    }
}
