package fci.swe.advanced_software.models;

import jakarta.persistence.*;

@Entity
@Table(name = "question_feedback")
public class QuestionFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_attempt_id", nullable = false)
    private QuizAttempt quizAttempt;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false)
    private String feedback; // Example: "Correct Answer: X"

    // Getters and setters
}
