package ar.edu.itba.paw.webapp.form.validation;

import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ExistingUserValidator implements ConstraintValidator<ExistingUser, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(ExistingUser user) {
    }

    @Override
    public boolean isValid(String userField,
                           ConstraintValidatorContext cxt) {

        if (userField == null) {
            return true;
        }

        if (userField.equals("")) {
            return true;
        }

        User u = userService.findByName(userField);

        return u != null;
    }
}
