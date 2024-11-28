package fci.swe.advanced_software.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_grades")
public class QuizGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "graded_by", nullable = true) // Nullable for auto-graded quizzes
    private User instructor;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = true)
    private String feedback; // Automated feedback (e.g., "Correct Answer: X")

    @Column(nullable = false)
    private LocalDateTime gradedAt;

    // Getters and setters
}
