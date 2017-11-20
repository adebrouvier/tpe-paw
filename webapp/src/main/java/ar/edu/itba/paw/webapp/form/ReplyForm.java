package ar.edu.itba.paw.webapp.form;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ReplyForm {

    @Size(max = 200)
    @NotBlank
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String reply;

    public String getReply(){
        return reply;
    }

    public void setReply(String reply){
        this.reply = reply;
    }
}
