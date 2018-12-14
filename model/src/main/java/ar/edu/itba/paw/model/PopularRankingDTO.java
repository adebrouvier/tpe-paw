package ar.edu.itba.paw.model;

public class PopularRankingDTO implements Comparable<PopularRankingDTO> {

  private Ranking ranking;

  private Long rankedUsers;

  public PopularRankingDTO(Ranking ranking, Long rankedUsers) {
    this.ranking = ranking;
    this.rankedUsers = rankedUsers;
  }

  public Ranking getRanking() {
    return ranking;
  }

  public void setRanking(Ranking ranking) {
    this.ranking = ranking;
  }

  public Long getRankedUsers() {
    return rankedUsers;
  }

  public void setRankedUsers(Long rankedUsers) {
    this.rankedUsers = rankedUsers;
  }

  @Override
  public int compareTo(PopularRankingDTO popularRankingDTO) {
    return popularRankingDTO.rankedUsers.compareTo(this.rankedUsers);
  }
}
