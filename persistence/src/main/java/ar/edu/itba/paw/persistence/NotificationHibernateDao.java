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
        /*em.createQuery("insert into Notification (user.id, type, date, description) " +
                "SELECT uf.userFollowerId, :type, :date, :description FROM UserFollow AS uf WHERE uf.userFollowedId = :userId")
                .setParameter("type", Notification.Types.PARTICIPATES_IN_TOURNAMENT.toString())
                .setParameter("date", date)
                .setParameter("description", description)
                .setParameter("userId", userFollowed.getId())
                .executeUpdate();*/
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

    @Override
    public List<Notification> getNotifications(User owner) {
        if(owner == null) {
            return null;
        }
        List<Notification> list = em.createQuery("FROM Notification as n WHERE n.user.id = :userId", Notification.class)
                .setParameter("userId", owner.getId())
                .getResultList();
        if(list == null || list.isEmpty()) {
            return null;
        }
        return list;
    }
}