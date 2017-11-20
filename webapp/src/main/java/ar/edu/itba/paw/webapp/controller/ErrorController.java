package ar.edu.itba.paw.webapp.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for each error page
 */
@Controller
public class ErrorController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorController.class);

    @RequestMapping("/404")
    public ModelAndView notFoundPage() {
        LOGGER.debug("Access to 404 page");
        return new ModelAndView("404");
    }

    @RequestMapping("/403")
    public ModelAndView forbidden() {
        LOGGER.debug("Access to 403 page");
        return new ModelAndView("403");
    }

    @RequestMapping("/500")
    public ModelAndView internalError() {
        LOGGER.error("Access to 500 page");
        return new ModelAndView("500");
    }
}
