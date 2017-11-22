package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "notification")
public class Notification {

    public enum Types {PARTICIPATES_IN_TOURNAMENT, FIRST_PLACE, SECOND_PLACE, THIRD_PLACE, ACCEPT_JOIN_TOURNAMENT, REJECT_JOIN_TOURNAMENT, REQUEST_JOIN_TOURNAMENT, REPLY_TOURNAMENT_COMMENT, ADD_TOURNAMENT_TO_RANKING}

    ;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_notification_id_seq")
    @SequenceGenerator(sequenceName = "notification_notification_id_seq",
            name = "notification_notification_id_seq", allocationSize = 1)
    @Column(name = "notification_id")
    private Long notificationId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date")
    private Date date;

    @Column(name = "description")
    private String description;

    @Transient
    List<String> decodeDescription = null;

    public Notification() {
        /* for Hibernate */
    }

    public Notification(String type, Boolean isRead, Date date, String description, User user) {
        this.type = type;
        this.isRead = isRead;
        this.date = date;
        this.description = description;
        this.user = user;
    }

    public Long getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Long notification_id) {
        this.notificationId = notification_id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
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
        if (decodeDescription == null) {
            decodeDescription();
        }
        return decodeDescription;
    }

    public void setDecodeDescription(List<String> decodeDescription) {
        this.decodeDescription = decodeDescription;
    }

    public static String encodeDescription(Notification.Types type, User user, Tournament tournament, Comment comment, Ranking ranking) {
        switch (type) {
            case PARTICIPATES_IN_TOURNAMENT:
            case FIRST_PLACE:
            case SECOND_PLACE:
            case THIRD_PLACE:
            case REQUEST_JOIN_TOURNAMENT:
                return encodeUserAndTournament(user, tournament);
            case ACCEPT_JOIN_TOURNAMENT:
            case REJECT_JOIN_TOURNAMENT:
                return encodeTournament(tournament);
            case REPLY_TOURNAMENT_COMMENT:
                return encodeUserTournamentComment(user, tournament, comment);
            case ADD_TOURNAMENT_TO_RANKING:
                return encodeUserTournamentRanking(user, tournament, ranking);
            default:
                return null;
        }
    }

    private static String encodeUserAndTournament(User user, Tournament tournament) {
        if (user == null || tournament == null) {
            return null;
        }
        String str = String.valueOf(user.getId()) + "/" + user.getName() + "/" +
                String.valueOf(tournament.getId()) + "/" + tournament.getName();
        return str;
    }

    private static String encodeUserTournamentComment(User user, Tournament tournament, Comment comment) {
        if (user == null || tournament == null || comment == null) {
            return null;
        }
        String str = String.valueOf(user.getId()) + "/" + user.getName() + "/" +
                String.valueOf(tournament.getId()) + "/" + tournament.getName() + "/" +
                String.valueOf(comment.getId());
        return str;
    }

    private static String encodeUserTournamentRanking(User user, Tournament tournament, Ranking ranking) {
        if (user == null || tournament == null || ranking == null) {
            return null;
        }
        String str = String.valueOf(user.getId()) + "/" + user.getName() + "/" +
                String.valueOf(tournament.getId()) + "/" + tournament.getName() + "/" +
                String.valueOf(ranking.getId()) + "/" + ranking.getName();
        return str;
    }

    private static String encodeTournament(Tournament tournament) {
        if (tournament == null) {
            return null;
        }
        String str = String.valueOf(tournament.getId()) + "/" + tournament.getName();
        return str;
    }

    private void decodeDescription() {
        String[] args = description.split("/");
        List<String> list = new ArrayList<>();
        int size = args.length;
        for (int i = 0; i < size; i++) {
            list.add(args[i]);
        }
        this.decodeDescription = list;
    }

}
