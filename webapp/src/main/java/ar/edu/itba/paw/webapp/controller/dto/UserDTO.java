package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.User;

import javax.validation.constraints.Size;

public class UserDTO {

    @Size(min = 6, max = 16)
    private String username;

    @Size(min = 6, max = 100)
    private String password;

    @Size(min = 6, max = 100)
    private String repeatPassword;

    private Long id;

    public UserDTO(){}

    public UserDTO(User user) {
        this.username = user.getName();
        this.id = user.getId();
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

    public String getPassword(){
      return password;
    }

    public void setPassword(String password){
      this.password = password;
    }

    public String getRepeatPassword(){
      return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword){
      this.repeatPassword = repeatPassword;
    }
}
