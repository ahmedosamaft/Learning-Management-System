package fci.swe.advanced_software.models.assessments;

import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Student;
import jakarta.persistence.*;

@Entity
public class Feedback extends AbstractEntity {

    private String comments;

    @Column
    private Integer grade;

    @OneToOne
    @JoinColumn(name = "attempt_id", nullable = false)
    private Attempt attempt;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FeedbackType feedbackType;
}
