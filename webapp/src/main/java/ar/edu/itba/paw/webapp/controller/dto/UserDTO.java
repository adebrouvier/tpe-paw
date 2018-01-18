package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.User;

public class UserDTO {

    private String username;
    private String password;
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
}
