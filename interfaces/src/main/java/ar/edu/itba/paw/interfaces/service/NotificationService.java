package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.*;

import java.util.List;

public interface NotificationService {

  public void createParticipatesInNotifications(User userFollowed, Tournament tournament);

  public List<Notification> getRecentNotifications(User owner);

  public List<Notification> getNotifications(User owner, int page);

  public void createAcceptJoinNotification(User acceptedUser, Tournament tournament);

  public void createRejectJoinNotification(User rejectUser, Tournament tournament);

  public void createRequestJoinNotification(User userRequesting, Tournament tournament);

  public void createReplyTournamentCommentsNotification(User userReplied, Comment commentReplied, Tournament tournament);

  public void createAddTournamentToRankingNotification(User owner, Tournament tournament, Ranking ranking);
}

