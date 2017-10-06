package ar.edu.itba.paw.webapp.form.validation;


import ar.edu.itba.paw.interfaces.service.GameService;
import ar.edu.itba.paw.model.Game;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class GameValidator implements ConstraintValidator<GameConstraint,String> {

    @Autowired
    private GameService gs;

    @Override
    public void initialize(GameConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        List<Game> list = gs.getGames();
        if(value == null){
            return true;
        }
        if(value.length() == 0) {
            return true;
        }
        for(Game game : list) {
            if(value.equals(game.getName())) {
                return true;
            }
        }
        return false;
    }
}
