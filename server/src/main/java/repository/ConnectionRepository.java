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
            "WITH created AS (INSERT INTO public.connections "
                    + " (requester_id, recipient_id, status) "
                    + " VALUES (:requesterId, :recipientId, :status)"
                    + " RETURNING recipient_id AS id " +
                    ")" +
                    " SELECT users.id, users.username, users.password, users.created_at, users.updated_at" +
                    " FROM created" +
                    " INNER JOIN users on users.id = created.id "; // returns the receiver id

    /**
     * - Update 'old status' status to given parameter.
     * - Return User object of user which is not making the request.
     * - If user is accepting friend request, user must get user object of the friend.
     */
    private static final String UPDATE =
            "with updated as ( "
                    + " update public.connections "
                    + "     set status = :status "
                    + " where requester_id = :requesterId and recipient_id = :recipientId and status = :oldStatus "
                    + " returning requester_id , recipient_id "
                    + " ), other as ( "
                    +      " select "
                    +       " case when requester_id <> :senderId then requester_id "
                    +       " when recipient_id  <> :senderId then recipient_id "
                    +       " end as id "
                    +      " from updated "
                    + " ) "
                    + " select "
                    + "     users.id, users.username, users.password, users.created_at, users.updated_at "
                    + " from other "
                    + " inner join users on users.id = other.id";

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

    public User save(Long requesterId, Long recipientId) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        entityManager.getTransaction().begin();

        User user = (User) entityManager.createNativeQuery(INSERT, User.class)
                .setParameter("requesterId", requesterId)
                .setParameter("recipientId", recipientId)
                .setParameter("status", ConnectionStatus.PENDING.toString())
                .getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();
        return user;
    }

    public User update(Long requesterId, Long recipientId, Long senderId, String status, String oldStatus) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        entityManager.getTransaction().begin();

        User user = (User) entityManager.createNativeQuery(UPDATE, User.class)
                .setParameter("oldStatus", oldStatus)
                .setParameter("senderId", senderId)
                .setParameter("requesterId", requesterId)
                .setParameter("recipientId", recipientId)
                .setParameter("status", status)
                .getSingleResult();

        entityManager.getTransaction().commit();
        entityManager.close();
        return user;
    }

}
