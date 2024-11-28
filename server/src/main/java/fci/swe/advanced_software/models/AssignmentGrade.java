package fci.swe.advanced_software.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "assignment_grades")
public class AssignmentGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "graded_by", nullable = false)
    private User instructor;

    @Column(nullable = false)
    private String grade;

    @Column(nullable = true)
    private String feedback;

    @Column(nullable = false)
    private LocalDateTime gradedAt;

    // Getters and setters
}
