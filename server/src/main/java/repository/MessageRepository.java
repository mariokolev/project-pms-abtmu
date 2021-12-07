package repository;

import config.EntityManagerConfig;
import entity.Message;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MessageRepository {

    public List<Message> findAllByConversationId(Long id) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m WHERE m.conversation.id = :id order by id DESC", Message.class);
        List<Message> messages = query.setParameter("id", id).getResultList();

        entityManager.close();
        return messages;
    }

    public Message save(Message message) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(message);

        entityManager.getTransaction().commit();
        entityManager.close();
        return message;
    }

    public Message update(Message message) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(message);

        entityManager.getTransaction().commit();
        entityManager.close();
        return message;
    }
}
