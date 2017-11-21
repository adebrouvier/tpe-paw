package ar.edu.itba.paw.service;

import ar.edu.itba.paw.interfaces.persistence.NotificationDao;
import ar.edu.itba.paw.interfaces.service.NotificationService;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationDao notificationDao;

    @Override
    public void createParticipatesInNotifications(User userFollowed, Tournament tournament) {
        notificationDao.createParticipatesInNotifications(userFollowed, tournament);
    }

    @Override
    public List<Notification> getRecentNotifications(User owner) {
        return notificationDao.getRecentNotifications(owner);
    }

    @Override
    public List<Notification> getNotifications(User owner, int page) {
        return notificationDao.getNotifications(owner, page);
    }

    @Transactional
    @Override
    public void createAcceptJoinNotification(User acceptedUser, Tournament tournament) {
        notificationDao.createAcceptJoinNotification(acceptedUser, tournament);
    }
}
