package fci.swe.advanced_software.models;

import jakarta.persistence.*;

@Entity
public class Lesson extends AbstractEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    private String otp;
}
