package ar.edu.itba.paw.interfaces.persistence;

public interface GameUrlImageDao {
    public void create(long gameId, String url);
    public String findById(long gameId);
}
