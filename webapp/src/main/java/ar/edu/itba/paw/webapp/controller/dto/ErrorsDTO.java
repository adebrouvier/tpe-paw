package ar.edu.itba.paw.webapp.controller.dto;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ErrorsDTO {

  private String message;
  private List<InvalidFieldDTO> errors;

  public ErrorsDTO() {
  }

  public ErrorsDTO(final String message) {
    this.setMessage(message);
  }

  public ErrorsDTO(final String message, final Set<? extends ConstraintViolation<?>> constraintViolations) {

    this.setMessage(message);
    errors = new ArrayList<>(constraintViolations.size());

    for (ConstraintViolation constraintViolation : constraintViolations) {
      if (!constraintViolation.getPropertyPath().toString().isEmpty()) {
        errors.add(new InvalidFieldDTO(constraintViolation));
      }
    }
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(final String message) {
    this.message = message;
  }

  public List<InvalidFieldDTO> getErrors() {
    return errors;
  }

  public void setErrors(final List<InvalidFieldDTO> errors) {
    this.errors = errors;
  }
}
