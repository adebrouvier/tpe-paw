package ar.edu.itba.paw.webapp.form;


import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataParam;

public class UserPictureForm {

  @FormDataParam("image")
  private FormDataBodyPart image;

  public FormDataBodyPart getImage() {
    return image;
  }

  public void setImage(FormDataBodyPart image) {
    this.image = image;
  }
}
