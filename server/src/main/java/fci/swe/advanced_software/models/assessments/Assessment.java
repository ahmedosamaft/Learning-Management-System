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
import java.util.HashSet;
import java.util.List;
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
    private List<Media> media;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL)
    private Set<Question> questions = new HashSet<>();

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL)
    private Set<Attempt> attempts = new HashSet<>();

    @Column(nullable = false)
    private Integer maxScore;

    @Column(nullable = false)
    private AssessmentType type;

    @Column(nullable = false)
    private Timestamp startsAt;

    @Column(nullable = false)
    private Timestamp endsAt;
}
