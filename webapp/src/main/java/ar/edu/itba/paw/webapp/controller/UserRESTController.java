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

    @PUT
    @Path("/{id}")
    @Produces(value = {MediaType.APPLICATION_JSON})
    @Consumes(value	=	{	MediaType.MULTIPART_FORM_DATA})
    public Response updateUser(@PathParam("id") final long id, @FormDataParam("user") final UserUpdateForm userForm, @BeanParam final UserPictureDto userPictureDto) throws ValidationException {

      User loggedUser = ss.getLoggedUser();
      final User u = us.findById(id);
      if(u == null) {
        return Response.status(Response.Status.NOT_FOUND).build();
      }
      if(!loggedUser.equals(u)) {
        return Response.status(Response.Status.FORBIDDEN).build();
      }

      validator.validate(userForm, "Failed to validate tournament");

      if (userForm == null || u == null) {
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
      final URI location = uriInfo.getAbsolutePathBuilder().path(String.valueOf(id)).build();

      return Response.created(location).build();

    }
}
