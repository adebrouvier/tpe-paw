package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.interfaces.service.SecurityService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.controller.dto.NotificationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;
import java.util.List;

@Path("notifications")
@Component
public class NotificationRESTController {


  @Autowired
  private NotificationService ns;

  @Autowired
  private UserService us;

  @Autowired
  private SecurityService ss;

  private static final Logger LOGGER = LoggerFactory.getLogger(UserRESTController.class);

  @GET
  @Path("/{username}")
  @Produces(value = { MediaType.APPLICATION_JSON, })
  public Response notification(@PathParam("username") String username, @QueryParam("page") final int page) {

    final User u = us.findByName(username);
    final User loggedUser = ss.getLoggedUser();
    if(u == null || page < 0) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
    /*
    if (loggedUser == null || u.getId() != loggedUser.getId()) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
    */

    List<Notification> notifications = ns.getNotifications(u, page);
    List<NotificationDTO> notificationsDTO = new LinkedList<>();
    for(Notification n : notifications) {
      notificationsDTO.add(new NotificationDTO(n));
    }
    return Response.ok(new GenericEntity<List<NotificationDTO>>(notificationsDTO) {}).build();
  }
}
