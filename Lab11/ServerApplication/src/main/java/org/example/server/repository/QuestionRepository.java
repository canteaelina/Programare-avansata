package org.example.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.server.entity.QuestionEntity;

public class QuestionRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GamePU");

    public void create(QuestionEntity question) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(question);
        em.getTransaction().commit();
        em.close();
    }

    public java.util.List<QuestionEntity> findAll() {
        EntityManager em = emf.createEntityManager();
        java.util.List<QuestionEntity> list = em.createQuery("SELECT q FROM QuestionEntity q", QuestionEntity.class).getResultList();
        em.close();
        return list;
    }
}