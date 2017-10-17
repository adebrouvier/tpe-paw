package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;import ar.edu.itba.paw.webapp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    UserService us;

    @RequestMapping("/login")
    public ModelAndView index(@ModelAttribute("loginForm") final UserForm loginForm) {
        return new ModelAndView("/");
    }

    @RequestMapping("/loginUser")
    public ModelAndView login(@Valid @ModelAttribute("userForm") final UserForm loginForm, final BindingResult errors) {
        if (errors.hasErrors()) {
            return index(loginForm);
        }

    }
}
