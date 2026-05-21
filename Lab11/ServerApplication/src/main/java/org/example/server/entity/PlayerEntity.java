package org.example.server.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "players")
public class PlayerEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "player_seq")
    @SequenceGenerator(name = "player_seq", sequenceName = "PLAYER_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // One-to-Many: Un jucător poate avea mai multe rezultate
    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL)
    private List<ResultEntity> results;

    public PlayerEntity() {}
    public PlayerEntity(String name) { this.name = name; }

    public Long getId() { return id; }
    public String getName() { return name; }
}