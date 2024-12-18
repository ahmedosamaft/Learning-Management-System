package fci.swe.advanced_software.models.assessments;

import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Media;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Assessment extends AbstractEntity {
    @Column(nullable = false)
    private String instructions;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL)
    private Set<Media> media;

    @ManyToMany(mappedBy = "assessment", cascade = CascadeType.ALL)
    @JoinTable(
            name = "question_assessment",
            joinColumns = @JoinColumn(name = "assessment_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> questions;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL)
    private Set<Attempt> attempts;

    @Column(nullable = false)
    private Integer maxScore;

    @Column(nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private AssessmentType type;

    @Column(nullable = false)
    private Timestamp startsAt;

    @Column(nullable = false)
    private Timestamp endsAt;
}
