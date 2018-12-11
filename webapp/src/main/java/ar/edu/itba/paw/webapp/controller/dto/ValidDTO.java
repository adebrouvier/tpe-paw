package ar.edu.itba.paw.webapp.controller.dto;

public class ValidDTO {

  private boolean valid;

  public ValidDTO(){}

  public ValidDTO(boolean valid){
    this.valid = valid;
  }

  public boolean isValid() {
    return valid;
  }

  public void setValid(boolean valid) {
    this.valid = valid;
  }
}
