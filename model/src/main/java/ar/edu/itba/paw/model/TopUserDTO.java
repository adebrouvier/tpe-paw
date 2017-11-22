package ar.edu.itba.paw.model;

public class TopUserDTO implements Comparable<TopUserDTO> {

    private User user;
    private Long wins;

    public TopUserDTO(User user, Long wins) {
        this.user = user;
        this.wins = wins;
    }

    TopUserDTO() {
        /* For Hibernate */
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getWins() {
        return wins;
    }

    public void setWins(Long wins) {
        this.wins = wins;
    }

    @Override
    public int compareTo(TopUserDTO topUserDTO) {
        return topUserDTO.wins.compareTo(this.wins);
    }
}
