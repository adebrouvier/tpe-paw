package ar.edu.itba.paw.webapp.controller.dto;

import javax.validation.ConstraintViolation;

public class InvalidFieldDTO {

  private String field;
  private String error;

  public InvalidFieldDTO() {}

  public InvalidFieldDTO(final ConstraintViolation<?> constraintViolation) {
    this.setField(constraintViolation.getPropertyPath().toString());
    this.setError(constraintViolation.getMessage());
  }

  public String getField() {
    return field;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public void setField(String field) {
    this.field = field;
  }
}
