package ar.edu.itba.paw.model;

public class Player {

    private String name;
    private long id;
    private long userId;
    private String userName;

    public Player(String name, long id) {
        this.name = name;
        this.id = id;
    }

    public Player(String name, long id, long userId){
        this.name = name;
        this.id = id;
        this.userId = userId;
    }

    public Player(String name, long id, long userId, String userName){
        this.name = name;
        this.id = id;
        this.userId = userId;
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public boolean hasUser() {
        if( userId == -1) {
            return false;
        }
        else
            return true;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        return id == player.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        return name + " - " + "Id: " + id;
    }

}
