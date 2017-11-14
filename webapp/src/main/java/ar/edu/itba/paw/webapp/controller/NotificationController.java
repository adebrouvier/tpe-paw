package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class NotificationController {

    @Autowired
    private NotificationService ns;

    @Autowired
    private UserService us;

    @RequestMapping("/user/{username}/notifications")
    public ModelAndView notification(@PathVariable String username, @ModelAttribute("loggedUser") User loggedUser){
        final User u = us.findByName(username);
        if(u == null) {
            return new ModelAndView("redirect:/404");
        }

        if(loggedUser == null || loggedUser.getId() != u.getId()) {
            return new ModelAndView("redirect:/403");
        }

        final ModelAndView mav = new ModelAndView("notifications");

        List<Notification> notifications = ns.getNotifications(u);
        mav.addObject("notifications", notifications);
        mav.addObject("user", u);
        return mav;
    }

    @ModelAttribute("loggedUser")
    public User loggedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username;
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return us.findByName(username);
    }
}
