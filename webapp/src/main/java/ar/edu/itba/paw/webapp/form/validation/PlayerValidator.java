package ar.edu.itba.paw.webapp.form.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PlayerValidator implements ConstraintValidator<PlayerConstraint,String>{

    @Override
    public void initialize(PlayerConstraint player) {
    }

    @Override
    public boolean isValid(String playerField,
                           ConstraintValidatorContext cxt) {
        String[] playerList = playerField.split("\r\n");

        return playerList.length >= 2;
    }
}
