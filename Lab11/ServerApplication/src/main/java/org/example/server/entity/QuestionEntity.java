package org.example.server.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class QuestionEntity extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_seq")
    @SequenceGenerator(name = "question_seq", sequenceName = "QUESTION_SEQUENCE", allocationSize = 1)
    private Long id;

    @Column(name = "text", nullable = false)
    private String text;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    public QuestionEntity() {}
    public QuestionEntity(String text, String correctAnswer) {
        this.text = text;
        this.correctAnswer = correctAnswer;
    }

    public Long getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}