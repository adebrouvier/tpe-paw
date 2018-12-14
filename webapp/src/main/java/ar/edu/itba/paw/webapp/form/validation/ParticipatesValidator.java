package ar.edu.itba.paw.webapp.form.validation;

import ar.edu.itba.paw.interfaces.service.TournamentService;
import ar.edu.itba.paw.interfaces.service.UserService;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ParticipatesValidator implements ConstraintValidator<Participates, String> {

  @Autowired
  private UserService userService;

  @Autowired
  private TournamentService tournamentService;

  @Override
  public void initialize(Participates constraintAnnotation) {
  }

  @Override
  public boolean isValid(String userField,
                         ConstraintValidatorContext cxt) {

    User user = userService.findByName(userField);

    if (user == null) {
      return true;
    }

    return true;
  }
}
