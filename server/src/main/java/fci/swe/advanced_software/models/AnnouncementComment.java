package fci.swe.advanced_software.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "announcement_comments")
public class AnnouncementComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User commenter;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime commentedAt;

    // Getters and setters
}
