package org.example.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.server.entity.ResultEntity;
import org.example.server.util.QueryLogger;

import java.util.List;

public class ResultRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GamePU");

    public void saveResult(ResultEntity result) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(result);
        em.getTransaction().commit();
        em.close();
    }
    public List<ResultEntity> getTop3() {
        EntityManager em = emf.createEntityManager();
        long startTime = System.currentTimeMillis();
        List<ResultEntity> list = null;
        try {
            // Am scos .setMaxResults(3) pentru compatibilitate cu Oracle 11g
            list = em.createQuery(
                            "SELECT r FROM ResultEntity r JOIN FETCH r.player ORDER BY r.score DESC, r.responseTimeMs ASC",
                            ResultEntity.class)
                    .getResultList();

            // Înregistrăm timpul de execuție în consolă și în fișierul .log!
            QueryLogger.logExecutionTime("getTop3Results", startTime, System.currentTimeMillis());
        } catch (Exception e) {
            QueryLogger.logException("getTop3Results", e);
        } finally {
            em.close();
        }
        return list;
    }
}