package fci.swe.advanced_software.models.courses;

import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.users.Student;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.sql.Timestamp;

@Entity
public class Attendance extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private Timestamp attendedAt;
}
