package ar.edu.itba.paw.interfaces.service;

import ar.edu.itba.paw.model.Match;

public interface MatchService {
  int BYE_WIN_SCORE = 1;

  /**
   * Creates a match with no defined participants.
   *
   * @param matchId         id of the match.
   * @param nextMatchId     id of the next match.
   * @param isNextMatchHome true if the player should be home in the next round, false otherwise.
   * @param tournamentId    id of the tournament that match belongs to.
   * @param standing        standing that the player would have if he loses the match.
   * @return the created match.
   */
  Match createEmpty(final int matchId, final int nextMatchId, final boolean isNextMatchHome, final long tournamentId, final int standing);

  /**
   * Creates a match between two players.
   *
   * @param matchId         id of the match.
   * @param nextMatchId     id of the next match.
   * @param isNextMatchHome true if the player should be home in the next round, false otherwise.
   * @param tournamentId    id of the tournament that match belongs to.
   * @param homePlayerId    id of the home player.
   * @param awayPlayerId    id of the away player.
   * @param standing        standing that the player would have if he loses the match.
   * @return the created match.
   */
  Match create(final int matchId, final int nextMatchId, final boolean isNextMatchHome, final long tournamentId, final long homePlayerId, final long awayPlayerId, final int standing);

  /**
   * Finds the Match with the specified id.
   *
   * @param id           id of the match.
   * @param tournamentId id of the tournament that the match belongs to.
   * @return the Match instance, or null of it doesn't exist.
   */
  Match findById(final int id, final long tournamentId);

  /**
   * Updates the score of the Match with the specified id.
   * If said match had its next match already played and
   * updating its result causes changes on the bracket, this
   * will reset the scores of the matches ahead.
   *
   * @param tournamentId id of the Tournament.
   * @param matchId      id of the Match.
   * @param homeScore    score of the home player.
   * @param awayScore    score of the away player.
   * @return the updated Match instance.
   */
  Match updateScore(final long tournamentId, final int matchId, final int homeScore, final int awayScore);

  void setVODLink(final String link, final long tournamentId, final int matchId);

  void setMap(final String mapName, final long tournamentId, final int matchId);

  void setHomePlayerCharacter(final String characterName, final long tournamentId, final int matchId);

  void setAwayPlayerCharacter(final String characterName, final long tournamentId, final int matchId);
}
