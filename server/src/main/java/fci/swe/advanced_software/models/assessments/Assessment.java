package fci.swe.advanced_software.models.assessments;

import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.courses.Course;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "assessment_type", discriminatorType = DiscriminatorType.STRING)
public class Assessment extends AbstractEntity {
    @Column(nullable = false)
    private String instructions;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "assessment_id", cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "assessment_id", cascade = CascadeType.ALL)
    private Set<Attempt> attempts = new HashSet<>();

    @Column(nullable = false)
    private Integer maxScore;

    @Column(nullable = false)
    private Timestamp startsAt;

    @Column(nullable = false)
    private Timestamp endsAt;
}
