package repository;

import common.Messages;
import config.EntityManagerConfig;
import entity.User;
import exception.BadRequestException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserRepository {

    public User findByUsername(String username) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        User user = null;

        try {
            TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
            user = query.setParameter("username", username).getSingleResult();

        } catch(NoResultException e) {
            throw new BadRequestException(Messages.USER_NOT_FOUND_USERNAME, username);
        } finally {
            entityManager.close();
        }

        return user;
    }
}
