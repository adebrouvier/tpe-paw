package ar.edu.itba.paw.interfaces.persistence;

public interface GameUrlImageDao {

    /**
     * Create a url that locate the game image
     * @param gameId id of the Game
     * @param url the url of the image
     */
    public void create(long gameId, String url);

    /**
     * Find the url that locate the game image
     * @param gameId id of the Game
     * @return the url of the image
     */
    public String findById(long gameId);
}
