package org.example.server.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.server.entity.GameEntity;
import org.example.server.entity.QuestionEntity;

public class GameRepository {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("GamePU");

    public GameEntity createGame() {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        GameEntity game = new GameEntity(); // Creează o sesiune nouă de joc
        em.persist(game);
        em.getTransaction().commit();
        em.close();
        return game;
    }

    public GameEntity createGame(java.util.List<QuestionEntity> questions) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        GameEntity game = new GameEntity();
        game.setQuestions(questions);
        em.persist(game);
        em.getTransaction().commit();
        em.close();
        return game;
    }
}