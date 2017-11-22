package ar.edu.itba.paw.model;

public class MostFollowedDTO implements Comparable<MostFollowedDTO>{

    private User user;
    private Long followers;

    public MostFollowedDTO(User user, Long followers){
        this.user = user;
        this.followers = followers;
    }

    MostFollowedDTO (){
        /* For Hibernate */
    }

    public User getUser(){
        return user;
    }

    public void setUser(User user){
        this.user = user;
    }

    public Long getFollowers() {
        return followers;
    }

    public void setFollowers(Long followers) {
        this.followers = followers;
    }

    @Override
    public int compareTo(MostFollowedDTO mostFollowedDTO) {
        return mostFollowedDTO.followers.compareTo(this.followers);
    }

}
