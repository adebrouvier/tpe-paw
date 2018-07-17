package ar.edu.itba.paw.webapp.form.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Component
public class RESTValidator {

  @Autowired
  private Validator validator;

  public <T> void validate(T object, String message, Class<?>... groups) throws ValidationException {

    final Set<ConstraintViolation<T>> constraintViolations = validator.validate(object, groups);

    if (!constraintViolations.isEmpty()) {
      throw new ValidationException(message, constraintViolations);
    }
  }
}
