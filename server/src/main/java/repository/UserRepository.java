package repository;

import config.EntityManagerConfig;
import entity.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

public class UserRepository {

    public User findByUsername(String username) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();

        TypedQuery<User> query = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class);
        User user = query.setParameter("username", username).getSingleResult();

        entityManager.close();
        return user;
    }
}
