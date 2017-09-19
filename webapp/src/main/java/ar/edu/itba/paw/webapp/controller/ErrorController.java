package ar.edu.itba.paw.webapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping("/404")
    public ModelAndView notFoundPage() {
        final ModelAndView mav = new ModelAndView("404");
        return mav;
    }
}
