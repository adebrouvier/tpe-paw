package ar.edu.itba.paw.webapp.controller;


import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.interfaces.service.SecurityService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.controller.dto.NotificationDTO;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
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
  @Produces(value = {MediaType.APPLICATION_JSON,})
  public Response notification(@QueryParam("page") final int page) {

    final User loggedUser = ss.getLoggedUser();

    if (loggedUser == null) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }

    if (page < 0) {
      return Response.status(Response.Status.NOT_FOUND).build();
    }

    List<Notification> notifications = ns.getNotifications(loggedUser, page);
    List<NotificationDTO> notificationsDTO = new LinkedList<>();
    if (notifications != null) {
      for (Notification n : notifications) {
        notificationsDTO.add(new NotificationDTO(n));
      }
      return Response.ok(new GenericEntity<List<NotificationDTO>>(notificationsDTO) {
      }).build();
    }
    return Response.ok(null).build();
  }

  @GET
  @Path("/unread")
  @Produces(value = {MediaType.APPLICATION_JSON,})
  public Response getUnreadNotifications() {
    final User loggedUser = ss.getLoggedUser();

    if (loggedUser == null) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }

    JSONObject object = new JSONObject();
    object.put("unreadNotifications", loggedUser.getUnreadNotifications());
    return Response.ok(object.toJSONString()).build();
  }
}
