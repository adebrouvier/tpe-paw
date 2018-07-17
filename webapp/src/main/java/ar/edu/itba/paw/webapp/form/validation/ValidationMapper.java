package ar.edu.itba.paw.webapp.form.validation;

import ar.edu.itba.paw.webapp.controller.dto.ErrorsDTO;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationMapper implements ExceptionMapper<ValidationException> {

  public final static Response.StatusType UNPROCESSABLE_ENTITY = new Response.StatusType() {

    @Override
    public int getStatusCode() {
      return 422;
    }

    @Override
    public String getReasonPhrase() {
      return "Unprocessable Entity";
    }

    @Override
    public Response.Status.Family getFamily() {
      return Response.Status.Family.CLIENT_ERROR;
    }
  };

  @Override
  public Response toResponse(final ValidationException exception) {
    return Response.status(UNPROCESSABLE_ENTITY).entity(new ErrorsDTO(exception.getMessage(), exception.getConstraintViolations())).build();
  }

}
