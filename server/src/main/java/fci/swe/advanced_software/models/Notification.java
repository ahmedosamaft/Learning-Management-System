package fci.swe.advanced_software.models;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.AbstractUser;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Notification extends AbstractEntity implements Comparable<Notification> {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "recipient", nullable = false)
    private AbstractUser recipient;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Override
    public int compareTo(Notification o) {
        if (getCreatedAt() == null || o.getCreatedAt() == null) {
            throw new IllegalArgumentException("CreatedAt cannot be null for comparison.");
        }
        return getCreatedAt().compareTo(o.getCreatedAt());
    }
}
