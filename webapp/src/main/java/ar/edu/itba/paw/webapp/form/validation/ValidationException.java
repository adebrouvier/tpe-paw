package ar.edu.itba.paw.webapp.form.validation;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class ValidationException extends Exception {

  private final Set<? extends ConstraintViolation<?>> constraintViolations;

  public ValidationException(final String message, final Set<? extends ConstraintViolation<?>> constraintViolations){
    super(message);
    this.constraintViolations = constraintViolations;
  }

  public Set<? extends ConstraintViolation<?>> getConstraintViolations() {
    return constraintViolations;
  }

}
