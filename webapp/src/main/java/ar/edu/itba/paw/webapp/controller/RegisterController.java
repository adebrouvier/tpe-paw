package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.RegisterForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class RegisterController {

    @Autowired
    private UserService us;

    @RequestMapping("/register")
    public ModelAndView register(@ModelAttribute("registerForm") final RegisterForm registerForm) {
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/registerUser", method = { RequestMethod.POST })
    public ModelAndView registerUser (@Valid @ModelAttribute("registerForm") final RegisterForm registerForm, final BindingResult errors){
        if (errors.hasErrors()) {
            return register(registerForm);
        }

        us.create(registerForm.getUsername(), registerForm.getPassword());

        return new ModelAndView("redirect:/");
    }

    @ModelAttribute("userId")
    public Integer loggedUser(final HttpSession session) {
        return (Integer) session.getAttribute("LOGGED_USER_ID");
    }

}

