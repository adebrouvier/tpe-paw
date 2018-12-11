package ar.edu.itba.paw.webapp.controller.dto;

import ar.edu.itba.paw.model.Ranking;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserFavoriteGame;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private String username;

    private Long id;

    private String description;

    private String twitchUrl;

    private String twitterUrl;

    private String youtubeUrl;

    private List<TournamentDTO> participates;

    private List<TournamentDTO> creates;

    private List<RankingDTO> rankings;

    private GameDTO favoriteGame;

    private Long followersAmount;

    private boolean isFollow;

    public UserDTO(){}

    public UserDTO(User user) {
        this.username = user.getName();
        this.id = user.getId();
        this.description = user.getDescription();
        this.twitchUrl = user.getTwitchUrl();
        this.youtubeUrl = user.getYoutubeUrl();
        this.twitterUrl = user.getTwitterUrl();
    }

    public UserDTO(User user,boolean isFollow, List<UserFavoriteGame> userFavoriteGames,Long followersAmount, List<Tournament> participates, List<Tournament> creates, List<Ranking> rankings) {
        if(user != null) {
          this.username = user.getName();
          this.isFollow = isFollow;
          this.id = user.getId();
          this.description = user.getDescription();
          this.twitchUrl = user.getTwitchUrl();
          this.youtubeUrl = user.getYoutubeUrl();
          this.twitterUrl = user.getTwitterUrl();
        }
        if(userFavoriteGames != null  && !userFavoriteGames.isEmpty()) {
          this.favoriteGame = new GameDTO(userFavoriteGames.get(0).getGame());
        }
        if(followersAmount != null) {
          this.followersAmount = followersAmount;
        }
        List<TournamentDTO> p = new ArrayList<>();
        List<TournamentDTO> c = new ArrayList<>();
        List<RankingDTO> r = new ArrayList<>();
        if(participates != null && !participates.isEmpty()) {
          for (Tournament paticipant : participates) {
            p.add(new TournamentDTO(paticipant));
          }
          this.participates = p;
        }
        if(creates != null && !creates.isEmpty()) {
          for(Tournament create : creates) {
            c.add(new TournamentDTO(create));
          }
          this.creates = c;
        }
        if(rankings != null && !rankings.isEmpty()) {
          for(Ranking ranking : rankings) {
            r.add(new RankingDTO(ranking));
          }
          this.rankings = r;
        }

    }

    public String getUsername(){
        return username;
    }

    public Long getId(){
        return id;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTwitchUrl() {
        return twitchUrl;
    }

    public void setTwitchUrl(String twitchUrl) {
        this.twitchUrl = twitchUrl;
    }

    public String getYoutubeUrl() {
        return youtubeUrl;
    }

    public void setYoutubeUrl(String youtubeUrl) {
        this.youtubeUrl = youtubeUrl;
    }

    public String getTwitterUrl() {
        return twitterUrl;
    }

    public void setTwitterUrl(String twitterUrl) {
        this.twitterUrl = twitterUrl;
    }

    public List<TournamentDTO> getParticipates() {
      return participates;
    }

    public void setParticipates(List<TournamentDTO> participates) {
      this.participates = participates;
    }

    public List<TournamentDTO> getCreates() {
      return creates;
    }

    public void setCreates(List<TournamentDTO> creates) {
      this.creates = creates;
    }

    public List<RankingDTO> getRankings() {
      return rankings;
    }

    public void setRankings(List<RankingDTO> rankings) {
      this.rankings = rankings;
    }

    public GameDTO getFavoriteGame() {
      return favoriteGame;
    }

    public void setFavoriteGame(GameDTO favoriteGame) {
      this.favoriteGame = favoriteGame;
    }

  public Long getFollowersAmount() {
    return followersAmount;
  }

  public void setFollowersAmount(Long followersAmount) {
    this.followersAmount = followersAmount;
  }

  public boolean isFollow() {
    return isFollow;
  }

  public void setFollow(boolean follow) {
    isFollow = follow;
  }
}
