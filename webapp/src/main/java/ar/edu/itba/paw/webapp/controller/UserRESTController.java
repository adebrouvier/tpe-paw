package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.controller.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;

@Path("users")
@Component
public class UserRESTController {

    @Autowired
    private UserService us;

    @Context
    private UriInfo uriInfo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRESTController.class);

    @GET
    @Path("/{id}")
    @Produces(value = { MediaType.APPLICATION_JSON, })
    public Response getById(@PathParam("id") final long id) {
        final User user = us.findById(id);
        if (user != null) {
            return Response.ok(new UserDTO(user)).build();
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
