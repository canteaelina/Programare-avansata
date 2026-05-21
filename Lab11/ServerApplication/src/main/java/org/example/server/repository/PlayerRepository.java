package org.example.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import org.example.server.entity.PlayerEntity;
import org.example.server.util.QueryLogger;

import java.util.List;

public class PlayerRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GamePU");

    // --- METODELE DE BAZĂ (CRUD) ---

    // Metoda CREATE: Salvează un jucător în baza de date
    public void create(PlayerEntity player) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(player); // Inserează obiectul în BD
        em.getTransaction().commit();
        em.close();
    }

    // Metoda READ (By ID): Găsește un jucător după ID-ul său
    public PlayerEntity findById(Long id) {
        EntityManager em = emf.createEntityManager();
        PlayerEntity player = em.find(PlayerEntity.class, id);
        em.close();
        return player;
    }


    // --- METODELE PENTRU HOMEWORK (JPQL) ---

    // 1. JPQL READ QUERY: Găsește toți jucătorii după un nume dat
    public List<PlayerEntity> findPlayersByName(String namePart) {
        EntityManager em = emf.createEntityManager();
        long startTime = System.currentTimeMillis();
        List<PlayerEntity> players = null;
        try {
            // JPQL pentru Selectare (READ)
            Query query = em.createQuery("SELECT p FROM PlayerEntity p WHERE p.name LIKE :name", PlayerEntity.class);
            query.setParameter("name", "%" + namePart + "%");
            players = query.getResultList();

            QueryLogger.logExecutionTime("findPlayersByName", startTime, System.currentTimeMillis());
        } catch (Exception e) {
            QueryLogger.logException("findPlayersByName", e);
        } finally {
            em.close();
        }
        return players;
    }

    // 2. JPQL MODIFYING QUERY (TRANZACȚIONAL): Actualizează numele unui jucător existent
    public void updatePlayerName(Long playerId, String newName) {
        EntityManager em = emf.createEntityManager();
        long startTime = System.currentTimeMillis();
        try {
            em.getTransaction().begin(); // Start tranzacție (Obligatoriu pentru Modifying)

            // JPQL pentru Update (MODIFYING)
            Query query = em.createQuery("UPDATE PlayerEntity p SET p.name = :newName WHERE p.id = :id");
            query.setParameter("newName", newName);
            query.setParameter("id", playerId);

            int updatedCount = query.executeUpdate(); // Executăm modificarea

            em.getTransaction().commit(); // Salvăm modificarea

            QueryLogger.logExecutionTime("updatePlayerName (rows updated: " + updatedCount + ")", startTime, System.currentTimeMillis());
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Anulăm modificările dacă a apărut o eroare
            }
            QueryLogger.logException("updatePlayerName", e);
        } finally {
            em.close();
        }
    }
}