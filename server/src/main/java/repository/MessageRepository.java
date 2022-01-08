package repository;

import config.EntityManagerConfig;
import entity.Message;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class MessageRepository implements BaseRepository<Message> {

    public List<Message> findAllByConversationId(Long id) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        TypedQuery<Message> query = entityManager.createQuery("SELECT m FROM Message m WHERE m.conversation.id = :id order by id DESC", Message.class);
        List<Message> messages = query.setParameter("id", id).getResultList();

        entityManager.close();
        return messages;
    }

    @Override
    public Message find(Long id) {
        return null;
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
