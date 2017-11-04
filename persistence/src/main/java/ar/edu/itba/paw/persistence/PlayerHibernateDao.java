package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.persistence.PlayerDao;
import ar.edu.itba.paw.interfaces.persistence.TournamentDao;
import ar.edu.itba.paw.model.Player;
import ar.edu.itba.paw.model.Tournament;
import ar.edu.itba.paw.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class PlayerHibernateDao implements PlayerDao{

    private static Integer DOWN_OFFSET = -1, UP_OFFSET = 1;

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private TournamentDao tournamentDao;

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
                "where p.tournament = :tournament_id and p.seed = :seed", Long.class);
        query.setParameter("seed", seed);
        query.setParameter("tournament_id", tournamentId);
        return query.getSingleResult();
    }

    @Override
    public void delete(long id) {
        final Query q = em.createQuery("DELETE from Player as p where p.id = :id");
        q.setParameter("id", id);
        q.executeUpdate();
    }

    @Override
    public List<Player> getTournamentPlayers(long tournamentId) {
        final TypedQuery<Player> query = em.createQuery("from Player as p " +
                "where p.tournament = :tournament_id", Player.class);
        query.setParameter("tournament_id", tournamentId);
        //TODO: check if null or empty list
        return query.getResultList();
    }

    @Override
    public boolean removeFromTournament(long tournamentId, long playerId) {

        /* Get the status of the tournament */
        final Tournament t = tournamentDao.findById(tournamentId);

        if (t == null) {
            return false;
        }

        /* If the tournament started, you can't remove players */
        if(!t.getStatus().equals(Tournament.Status.NEW)) {
            return false;
        }

        final Player p = findById(playerId);
        int removedSeed = p.getSeed();

        /* Update the seed of the other players */
        final Query updateSeed = em.createQuery("UPDATE Player as p SET p.seed = p.seed + :offset " +
                "WHERE p.seed > :seed AND p.tournament = :tournament");
        updateSeed.setParameter("offset", DOWN_OFFSET);
        updateSeed.setParameter("seed", removedSeed);
        updateSeed.setParameter("tournament", tournamentId);
        updateSeed.executeUpdate();

        /* Delete the player */
        final Query update = em.createQuery("DELETE FROM Player as p WHERE p.tournament = :tournament AND p.id = :player_id ");
        update.setParameter("tournament", tournamentId);
        update.setParameter("player_id", playerId);
        update.executeUpdate();
        return true;
    }

    @Override
    public boolean changeSeed(long tournamentId, int playerOldSeed, int playerNewSeed) {

        /* Get the status of the tournament */
        final Tournament t = tournamentDao.findById(tournamentId);

        if (t == null) {
            return false;
        }

        /* If the tournament started, you can't remove players */
        if(!t.getStatus().equals(Tournament.Status.NEW)) {
            return false;
        }

        Query q;

        if(playerOldSeed < playerNewSeed) {
            q = em.createQuery("UPDATE Player as p SET p.seed = p.seed + :offset " +
                    "WHERE p.tournament = :tournament AND p.seed > :old_seed AND p.seed <= :new_seed");
            q.setParameter("offset", DOWN_OFFSET);
            q.setParameter("tournament", tournamentId);
            q.setParameter("old_seed", playerOldSeed);
            q.setParameter("new_seed", playerNewSeed);
            q.executeUpdate();

        } else {
            q = em.createQuery("UPDATE Player as p SET p.seed = p.seed + :offset " +
                    "WHERE p.tournament = :tournament AND p.seed >= :new_seed AND p.seed < :old_seed");
            q.setParameter("offset", UP_OFFSET);
            q.setParameter("tournament", tournamentId);
            q.setParameter("old_seed", playerOldSeed);
            q.setParameter("new_seed", playerNewSeed);
            q.executeUpdate();
        }
        /* Change player seed */
        Long playerId = findBySeed(playerOldSeed, tournamentId);
        q = em.createQuery("UPDATE Player as p SET p.seed = :new_seed WHERE p.tournament = :tournament AND p.id = :id");
        q.setParameter("new_seed", playerNewSeed);
        q.setParameter("tournament", tournamentId);
        q.setParameter("id", playerId);
        q.executeUpdate();
        return true;
    }

    @Override
    public void setDefaultStanding(int standing, long tournamentId) {
        final Query query = em.createQuery("update Player as p set p.standing = :standing where p.tournament = :tournament");
        query.setParameter("standing", standing);
        query.setParameter("tournament", tournamentId);
        query.executeUpdate();
    }

    @Override
    public void addToTournament(long playerId, long tournamentId) {
        int seed = getNextSeed(tournamentId);
        final Query query = em.createQuery("update Player as p set p.tournament = :tournament, p.seed = :seed where p.id = :player_id");
        query.setParameter("tournament", tournamentId);
        query.setParameter("seed", seed);
        query.setParameter("player_id", playerId);
        query.executeUpdate();
    }

    /**
     * Gets the next available seed of a tournament
     * @param tournamentId id of the tournament
     * @return the next available seed
     */
    private int getNextSeed(long tournamentId) {
        final TypedQuery<Integer> query = em.createQuery("select COALESCE(max(p.seed), 0) from Player as p " +
                "where p.tournament = :tournament_id", Integer.class);
        query.setParameter("tournament_id", tournamentId);
        return 1 + query.getSingleResult();
    }
}
