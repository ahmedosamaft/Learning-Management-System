package fci.swe.advanced_software.models;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.AbstractUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
public class Notification extends AbstractEntity implements Comparable<Notification> {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isRead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient", nullable = false)
    private AbstractUser recipient;

    @ManyToOne(fetch = FetchType.LAZY)
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
