package org.example.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "results")
public class ResultEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "result_seq")
    @SequenceGenerator(name = "result_seq", sequenceName = "RESULT_SEQUENCE", allocationSize = 1)
    private Long id;

    // Many-to-One: Mai multe rezultate aparțin unui singur joc
    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    // Many-to-One: Mai multe rezultate aparțin unui singur jucător
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @Column(name = "score")
    private int score;

    @Column(name = "response_time_ms")
    private long responseTimeMs;

    public ResultEntity() {}
    public ResultEntity(GameEntity game, PlayerEntity player, int score, long responseTimeMs) {
        this.game = game;
        this.player = player;
        this.score = score;
        this.responseTimeMs = responseTimeMs;
    }

    public GameEntity getGame() {
        return game;
    }

    public PlayerEntity getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public long getResponseTimeMs() {
        return responseTimeMs;
    }
}