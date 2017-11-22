package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.NotificationDao;
import ar.edu.itba.paw.interfaces.persistence.UserFollowDao;
import ar.edu.itba.paw.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
public class NotificationHibernateDao implements NotificationDao {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserFollowDao userFollowDao;

    @Transactional
    @Override
    public void createParticipatesInNotifications(User userFollowed, Tournament tournament) {
        if (userFollowed == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.PARTICIPATES_IN_TOURNAMENT, userFollowed, tournament, null, null);
        Date date = new Date();
        Notification notification;

        List<UserFollow> list = userFollowDao.getFollowers(userFollowed);
        if (list != null) {
            for (UserFollow userFollow : list) {
                notification = new Notification(Notification.Types.PARTICIPATES_IN_TOURNAMENT.toString(), false, date, description, userFollow.getUserFollower());
                em.persist(notification);
            }
        }
    }

    @Override
    public void createFisrtPlaceNotifications(User user, Tournament tournament) {
        if (user == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.FIRST_PLACE, user, tournament, null, null);
        Date date = new Date();
        Notification notification;

        List<UserFollow> list = userFollowDao.getFollowers(user);
        if (list != null) {
            for (UserFollow userFollow : list) {
                notification = new Notification(Notification.Types.FIRST_PLACE.toString(), false, date, description, userFollow.getUserFollower());
                em.persist(notification);
            }
        }
    }

    @Override
    public void createSecondPlaceNotifications(User user, Tournament tournament) {
        if (user == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.SECOND_PLACE, user, tournament, null, null);
        Date date = new Date();
        Notification notification;

        List<UserFollow> list = userFollowDao.getFollowers(user);
        if (list != null) {
            for (UserFollow userFollow : list) {
                notification = new Notification(Notification.Types.SECOND_PLACE.toString(), false, date, description, userFollow.getUserFollower());
                em.persist(notification);
            }
        }
    }

    @Override
    public void createThirdPlaceNotifications(User user, Tournament tournament) {
        if (user == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.THIRD_PLACE, user, tournament, null, null);
        Date date = new Date();
        Notification notification;

        List<UserFollow> list = userFollowDao.getFollowers(user);
        if (list != null) {
            for (UserFollow userFollow : list) {
                notification = new Notification(Notification.Types.THIRD_PLACE.toString(), false, date, description, userFollow.getUserFollower());
                em.persist(notification);
            }
        }
    }

    @Override
    public List<Notification> getRecentNotifications(User owner) {
        if (owner == null) {
            return null;
        }
        List<Notification> list = em.createQuery("FROM Notification as n WHERE n.user.id = :userId", Notification.class)
                .setParameter("userId", owner.getId())
                .setMaxResults(5)
                .getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }

    @Transactional
    @Override
    public List<Notification> getNotifications(User owner, int page) {
        if (owner == null) {
            return null;
        }
        List<Notification> list = em.createQuery("FROM Notification as n WHERE n.user.id = :userId ORDER BY n.date DESC", Notification.class)
                .setParameter("userId", owner.getId())
                .setMaxResults(5)
                .setFirstResult(page * 5)
                .getResultList();

        if (list == null || list.isEmpty()) {
            return null;
        }

        em.createQuery("UPDATE Notification AS n SET n.isRead = true WHERE n.user.id = :userId")
                .setParameter("userId", owner.getId())
                .executeUpdate();
        return list;
    }

    @Override
    public void createAcceptJoinNotification(User acceptedUser, Tournament tournament) {
        if (acceptedUser == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.ACCEPT_JOIN_TOURNAMENT, null, tournament, null, null);
        Date date = new Date();
        Notification notification = new Notification(Notification.Types.ACCEPT_JOIN_TOURNAMENT.toString(), false, date, description, acceptedUser);
        em.persist(notification);
    }

    @Override
    public void createRejectJoinNotification(User rejectUser, Tournament tournament) {
        if (rejectUser == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.REJECT_JOIN_TOURNAMENT, null, tournament, null, null);
        Date date = new Date();
        Notification notification = new Notification(Notification.Types.REJECT_JOIN_TOURNAMENT.toString(), false, date, description, rejectUser);
        em.persist(notification);
    }

    @Override
    public void createRequestJoinNotification(User userRequesting, Tournament tournament) {
        if (userRequesting == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.REQUEST_JOIN_TOURNAMENT, userRequesting, tournament, null, null);
        Date date = new Date();
        Notification notification = new Notification(Notification.Types.REQUEST_JOIN_TOURNAMENT.toString(), false, date, description, tournament.getCreator());
        em.persist(notification);
    }

    @Override
    public void createReplyTournamentCommentsNotification(User userReplied, Comment commentReplied, Tournament tournament) {
        if (userReplied == null || commentReplied == null || tournament == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.REPLY_TOURNAMENT_COMMENT, userReplied, tournament, commentReplied, null);
        Date date = new Date();
        Notification notification = new Notification(Notification.Types.REPLY_TOURNAMENT_COMMENT.toString(), false, date, description, commentReplied.getCreator());
        em.persist(notification);
    }

    @Override
    @Transactional
    public void createAddTournamentToRankingNotification(User owner, Tournament tournament, Ranking ranking) {
        if (owner == null || tournament == null || ranking == null) {
            return;
        }
        String description = Notification.encodeDescription(Notification.Types.ADD_TOURNAMENT_TO_RANKING, owner, tournament, null, ranking);
        Date date = new Date();
        Notification notification;

        List<UserFollow> list = userFollowDao.getFollowers(owner);
        if (list != null) {
            for (UserFollow userFollow : list) {
                notification = new Notification(Notification.Types.ADD_TOURNAMENT_TO_RANKING.toString(), false, date, description, userFollow.getUserFollower());
                em.persist(notification);
            }
        }
    }
}
