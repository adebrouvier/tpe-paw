package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for each error page
 */
@Controller
public class ErrorController {

    @Autowired
    private UserService us;

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("/404")
    public ModelAndView notFoundPage(@ModelAttribute("loggedUser") User loggedUser) {
        ModelAndView mav = new ModelAndView("404");
        LOGGER.debug("Access to 404 page");
        mav.addObject("loggedUser", loggedUser);
        return mav;
    }

    @RequestMapping("/403")
    public ModelAndView forbidden(@ModelAttribute("loggedUser") User loggedUser) {
        ModelAndView mav = new ModelAndView("403");
        LOGGER.debug("Access to 403 page");
        mav.addObject("loggedUser", loggedUser);
        return mav;
    }

    @RequestMapping("/500")
    public ModelAndView internalError(@ModelAttribute("loggedUser") User loggedUser) {
        ModelAndView mav = new ModelAndView("500");
        LOGGER.error("Access to 500 page");
        mav.addObject("loggedUser", loggedUser);
        return mav;
    }

    @ModelAttribute("loggedUser")
    public User loggedUser() {
        if(SecurityContextHolder.getContext() == null ||
                SecurityContextHolder.getContext().getAuthentication() == null ||
                SecurityContextHolder.getContext().getAuthentication().getPrincipal() == null) {
            return null;
        }
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
