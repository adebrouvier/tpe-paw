package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PlayerHibernateDao implements PlayerDao{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Player create(String name) {
        final Player player = new Player(name);
        em.persist(player);
        return player;
    }

    @Override
    public Player create(String name, User user) {
        final Player player = new Player(name, user);
        em.persist(player);
        return player;
    }

    @Override
    public Player findById(long id) {
        return em.find(Player.class, id);
    }

    @Override
    public long findBySeed(int seed, long tournamentId) {
        final TypedQuery<Long> query = em.createQuery("select p.id from Player as p " +
                "where p.tournament_id = :tournament_id and p.seed = :seed", Long.class);
        query.setParameter("seed", seed);
        query.setParameter("tournament_id", tournamentId);
        return query.getSingleResult();
    }

    @Override
    public void delete(long id) {
    //TODO
    }

    @Override
    public List<Player> getTournamentPlayers(long tournamentId) {
        //TODO
        return null;
    }

    @Override
    public boolean removeFromTournament(long tournamentId, long playerId) {
        //TODO
        return false;
    }

    @Override
    public boolean changeSeed(long tournamentId, int playerOldSeed, int playerNewSeed) {
        //TODO
        return false;
    }

    @Override
    public void setDefaultStanding(int standing, long tournamentId) {
        //TODO
    }

    @Override
    public void addToTournament(long playerId, long tournamentId) {
        //TODO
    }
}
