package ar.edu.itba.paw.webapp.controller.dto;


import ar.edu.itba.paw.model.Notification;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class NotificationDTO {

  private Long notificationId;

  private UserDTO user;

  private String type;

  private Boolean isRead;

  private Date date;

  private String description;

  private List<String> decodeDescription;

  public NotificationDTO() {

  }

  public NotificationDTO(Notification notification) {
    this.notificationId = notification.getNotificationId();
    this.user = new UserDTO(notification.getUser());
    this.type = notification.getType();
    this.isRead = notification.getIsRead();
    this.date = notification.getDate();
    this.description = notification.getDescription();
    if (notification.getDecodeDescription() == null) {
      this.decodeDescription = new LinkedList<>();
    } else {
      this.decodeDescription = notification.getDecodeDescription();
    }
  }

  public Long getNotificationId() {
    return notificationId;
  }

  public void setNotificationId(Long notificationId) {
    this.notificationId = notificationId;
  }

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Boolean getRead() {
    return isRead;
  }

  public void setRead(Boolean read) {
    isRead = read;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public List<String> getDecodeDescription() {
    return decodeDescription;
  }

  public void setDecodeDescription(List<String> decodeDescription) {
    this.decodeDescription = decodeDescription;
  }
}
