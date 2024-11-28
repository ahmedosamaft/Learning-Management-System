package fci.swe.advanced_software.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "announcements")
public class Announcement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = true)
    private Course course; // Announcements may be course-specific or system-wide.

    @ManyToOne
    @JoinColumn(name = "posted_by", nullable = false)
    private User postedBy; // The instructor or admin who made the announcement.

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime postedAt;

    @Column(nullable = false)
    private boolean isSystemWide; // Whether this announcement is for all users or course-specific.

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnnouncementMedia> media;

    @OneToMany(mappedBy = "announcement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnnouncementComment> comments;

    @Column(nullable = false)
    private boolean isPublished; // Indicates if the announcement is visible to users.

    // Getters and setters
}
