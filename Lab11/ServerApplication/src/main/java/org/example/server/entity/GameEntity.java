package org.example.server.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "games")
public class GameEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "game_seq")
    @SequenceGenerator(name = "game_seq", sequenceName = "GAME_SEQUENCE", allocationSize = 1)
    private Long id;

    // Many-to-Many: Un joc conține mai multe întrebări, o întrebare poate fi în mai multe jocuri
    @ManyToMany
    @JoinTable(
            name = "game_questions",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<QuestionEntity> questions;

    // One-to-Many: Un joc are mai multe rezultate
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<ResultEntity> results;

    public GameEntity() {}

    public List<QuestionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionEntity> questions) {
        this.questions = questions;
    }
}