package ar.edu.itba.paw.webapp.controller.dto;


import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class UserPictureDto {

  @FormDataParam("image")
  private FormDataBodyPart image;

  public UserPictureDto() {

  }

  public UserPictureDto(FormDataBodyPart image) {
    this.image = image;
  }

  public FormDataBodyPart getImage() {
    return image;
  }

  public void setImage(FormDataBodyPart image) {
    this.image = image;
  }
}
