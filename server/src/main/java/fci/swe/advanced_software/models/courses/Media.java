package fci.swe.advanced_software.models.courses;

import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.assessments.Assignment;
import jakarta.persistence.*;

@Entity
public class Media extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "announcement_id")
    private Announcement announcement;

    @ManyToOne
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

    @Column(nullable = false)
    private String realName;

    @Column(nullable = false)
    private String url;
}
