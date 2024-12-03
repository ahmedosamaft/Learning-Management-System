package fci.swe.advanced_software.models;

import jakarta.persistence.*;

@Entity
public class Enrollment extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column
    private String grade;
}
