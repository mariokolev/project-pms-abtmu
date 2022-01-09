package repository;

import common.Messages;
import config.EntityManagerConfig;
import entity.Conversation;
import entity.User;
import exception.BadRequestException;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class ConversationRepository implements BaseRepository<Conversation> {

    public List<Conversation> findAllByUserId(Long id) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();

        TypedQuery<Conversation> query = entityManager.createQuery("SELECT c FROM Conversation c inner join c.users user WHERE user.id = :id order by c.id desc", Conversation.class);
        List<Conversation> conversations = null;

        try {
            conversations = query.setParameter("id", id).getResultList();
        } catch (Exception e) {
            throw new BadRequestException(Messages.CONVERSATION_BY_USER_ID_NOT_FOUND, id);
        }

        entityManager.close();
        return conversations;
    }

    public Conversation find(Long id) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        Conversation conversation = null;

        try {
            conversation = entityManager.find(Conversation.class, id);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException(Messages.CONVERSATION_NOT_FOUND, id);
        }
        return conversation;
    }
}
