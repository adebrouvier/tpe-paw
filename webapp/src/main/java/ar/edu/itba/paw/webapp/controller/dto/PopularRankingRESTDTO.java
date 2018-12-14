package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.PopularRankingDTO;

public class PopularRankingRESTDTO {

  private RankingDTO ranking;
  private Long rankedUsers;

  public PopularRankingRESTDTO() {
  }

  public PopularRankingRESTDTO(PopularRankingDTO popularRankingDTO) {
    this.ranking = new RankingDTO(popularRankingDTO.getRanking());
    this.rankedUsers = popularRankingDTO.getRankedUsers();
  }

  public RankingDTO getRanking() {
    return ranking;
  }

  public void setRanking(RankingDTO ranking) {
    this.ranking = ranking;
  }

  public Long getRankedUsers() {
    return rankedUsers;
  }

  public void setRankedUsers(Long rankedUsers) {
    this.rankedUsers = rankedUsers;
  }
}
