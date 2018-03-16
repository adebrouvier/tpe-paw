package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.webapp.form.validation.FieldMatch;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO {

    private String username;

    private Long id;

    private String description;

    private String twitchUrl;

    private String twitterUrl;

    private String youtubeUrl;

    public UserDTO(){}

    public UserDTO(User user) {
        this.username = user.getName();
        this.id = user.getId();
        this.description = user.getDescription();
        this.twitchUrl = user.getTwitchUrl();
        this.youtubeUrl = user.getYoutubeUrl();
        this.twitterUrl = user.getTwitterUrl();
    }

    public String getUsername(){
        return username;
    }

    public Long getId(){
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTwitchUrl() {
        return twitchUrl;
    }

    public void setTwitchUrl(String twitchUrl) {
        this.twitchUrl = twitchUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

}
