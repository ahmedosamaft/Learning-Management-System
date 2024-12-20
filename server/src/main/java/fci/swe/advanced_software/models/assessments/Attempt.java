package fci.swe.advanced_software.models.assessments;

import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.users.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.Set;
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Attempt extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "assessment_id", nullable = false)
    private Assessment assessment;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @OneToOne
    @JoinColumn(name = "feedback_id")
    private Feedback feedback;

    @OneToMany(mappedBy = "attempt", cascade = CascadeType.ALL)
    private Set<Answer> answers;

    @Column(nullable = false)
    private Timestamp attemptedAt;
}
