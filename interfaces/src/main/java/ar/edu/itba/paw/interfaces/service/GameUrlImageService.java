package ar.edu.itba.paw.interfaces.service;

public interface GameUrlImageService {
    public String findById(long gameId);
    public void create(long gameId, String url);
}
