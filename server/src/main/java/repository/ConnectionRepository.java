package repository;

import common.ConnectionStatus;
import config.EntityManagerConfig;
import entity.User;

import javax.persistence.EntityManager;
import java.util.List;

public class ConnectionRepository {

    private static final String FIND_ALL_BY_USER_ID =
            "select id, username, password, created_at, updated_at "
                    + "from users where id in "
                    + "( "
                    + "  select "
                    + "     case "
                    + "         when requester_id <> :id then requester_id "
                    + "         when recipient_id <> :id then recipient_id "
                    + "     end as id "
                    + " from connections "
                    + " where (requester_id = :id or recipient_id = :id) and status = :status "
                    + ")";

    private static final String INSERT =
            "INSERT INTO public.connections "
                    + " (requester_id, recipient_id, status) "
                    + " VALUES (:requesterId, :recipientId, :status)";

    @SuppressWarnings("unchecked")
    public List<User> findAllByUserId(Long id, String status) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();

        List<User> users = entityManager.createNativeQuery(FIND_ALL_BY_USER_ID, User.class)
                .setParameter("id", id)
                .setParameter("status", status)
                .getResultList();

        entityManager.close();
        return users;
    }

    public void save(Long requesterId, Long recipientId) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        entityManager.getTransaction().begin();

        entityManager.createNativeQuery(INSERT)
                .setParameter("requesterId", requesterId)
                .setParameter("recipientId", recipientId)
                .setParameter("status", ConnectionStatus.PENDING.toString())
                .executeUpdate();

        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
