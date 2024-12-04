package fci.swe.advanced_software.models.courses;

import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.assessments.Assignment;
import fci.swe.advanced_software.models.assessments.Quiz;
import fci.swe.advanced_software.models.users.Instructor;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Course extends AbstractEntity {
    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "instructor_id", nullable = false)
    private Instructor instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Lesson> lessons = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Assignment> assignments = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    private Set<Quiz> quizzes = new HashSet<>();
}
