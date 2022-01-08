package repository;

import config.EntityManagerConfig;
import entity.Conversation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ConversationRepository implements BaseRepository<Conversation> {

    public List<Conversation> findAllByUserId(Long id) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();

        TypedQuery<Conversation> query = entityManager.createQuery("SELECT c FROM Conversation c inner join c.users user WHERE user.id = :id order by c.id desc", Conversation.class);
        List<Conversation> conversations = query.setParameter("id", id).getResultList();

        entityManager.close();
        return conversations;
    }

    public Conversation find(Long id) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        return entityManager.find(Conversation.class, id);
    }
}
