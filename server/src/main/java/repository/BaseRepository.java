package repository;

import config.EntityManagerConfig;

import javax.persistence.EntityManager;

public interface BaseRepository<T> {

    T find(Long id);

    default T save(T object) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(object);

        entityManager.getTransaction().commit();
        entityManager.close();
        return object;
    }

    default T update(T object) {
        EntityManager entityManager = EntityManagerConfig.getInstance().createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(object);

        entityManager.getTransaction().commit();
        entityManager.close();
        return object;
    }
}
