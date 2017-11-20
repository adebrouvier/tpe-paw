package ar.edu.itba.paw.interfaces.persistence;

import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;

import java.util.List;

public interface NotificationDao {

    public void createParticipatesInNotifications(User userFollowed, Tournament tournament);
    public List<Notification> getRecentNotifications(User owner);
    public List<Notification> getNotifications(User owner);
}
