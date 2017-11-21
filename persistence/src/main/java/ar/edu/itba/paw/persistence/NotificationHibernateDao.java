package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.NotificationDao;
import ar.edu.itba.paw.interfaces.persistence.UserFollowDao;
import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserFollow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class NotificationHibernateDao implements NotificationDao{

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserFollowDao userFollowDao;

    @Transactional
    @Override
    public void createParticipatesInNotifications(User userFollowed, Tournament tournament) {
        if(userFollowed == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.PARTICIPATES_IN_TOURNAMENT, userFollowed, tournament, null);
        Date date = new Date();
        Notification notification = new Notification(Notification.Types.PARTICIPATES_IN_TOURNAMENT.toString(), false, date, description);

        List<UserFollow> list = userFollowDao.getFollowers(userFollowed);
        if(list != null) {
            for (UserFollow userFollow : list) {
                notification.setUser(userFollow.getUserFollower());
                em.persist(notification);
            }
        }
    }

    @Override
    public void createFisrtPlaceNotifications(User user, Tournament tournament) {
        if(user == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.FIRST_PLACE, user, tournament, null);
        Date date = new Date();
        Notification notification = new Notification(Notification.Types.FIRST_PLACE.toString(), false, date, description);

        List<UserFollow> list = userFollowDao.getFollowers(user);
        if(list != null) {
            for (UserFollow userFollow : list) {
                notification.setUser(userFollow.getUserFollower());
                em.persist(notification);
            }
        }
    }

    @Override
    public void createSecondPlaceNotifications(User user, Tournament tournament) {
        if(user == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.SECOND_PLACE, user, tournament, null);
        Date date = new Date();
        Notification notification = new Notification(Notification.Types.SECOND_PLACE.toString(), false, date, description);

        List<UserFollow> list = userFollowDao.getFollowers(user);
        if(list != null) {
            for (UserFollow userFollow : list) {
                notification.setUser(userFollow.getUserFollower());
                em.persist(notification);
            }
        }
    }

    @Override
    public void createThirdPlaceNotifications(User user, Tournament tournament) {
        if(user == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.THIRD_PLACE, user, tournament, null);
        Date date = new Date();
        Notification notification = new Notification(Notification.Types.THIRD_PLACE.toString(), false, date, description);

        List<UserFollow> list = userFollowDao.getFollowers(user);
        if(list != null) {
            for (UserFollow userFollow : list) {
                notification.setUser(userFollow.getUserFollower());
                em.persist(notification);
            }
        }
    }

    @Override
    public List<Notification> getRecentNotifications(User owner) {
        if(owner == null) {
            return null;
        }
        List<Notification> list = em.createQuery("FROM Notification as n WHERE n.user.id = :userId", Notification.class)
                .setParameter("userId", owner.getId())
                .setMaxResults(5)
                .getResultList();
        if(list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Transactional
    @Override
    public List<Notification> getNotifications(User owner, int page) {
        if(owner == null) {
            return null;
        }
        List<Notification> list = em.createQuery("FROM Notification as n WHERE n.user.id = :userId ORDER BY n.date DESC", Notification.class)
                .setParameter("userId", owner.getId())
                .setMaxResults(5)
                .setFirstResult(page*5)
                .getResultList();

        if(list == null || list.isEmpty()) {
            return null;
        }

        em.createQuery("UPDATE Notification AS n SET n.isRead = true WHERE n.user.id = :userId")
                .setParameter("userId", owner.getId())
                .executeUpdate();
        return list;
    }

    @Override
    public void createAcceptJoinNotification(User acceptedUser, Tournament tournament) {
        if(acceptedUser == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.ACCEPT_JOIN_TOURNAMENT, null, tournament, null);
        Date date = new Date();
        Notification notification = new Notification(Notification.Types.ACCEPT_JOIN_TOURNAMENT.toString(), false, date, description);

        notification.setUser(acceptedUser);
        em.persist(notification);
    }
}
