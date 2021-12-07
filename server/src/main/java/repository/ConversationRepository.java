package repository;

import config.EntityManagerConfig;
import entity.Conversation;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ConversationRepository {

    public List<Conversation> findALlByUserId(Long id) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();

        TypedQuery<Conversation> query = entityManager.createQuery("SELECT c FROM Conversation c inner join c.users user WHERE user.id = :id order by c.id desc", Conversation.class);
        List<Conversation> conversations = query.setParameter("id", id).getResultList();

        entityManager.close();
        return conversations;
    }
}
