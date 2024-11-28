package fci.swe.advanced_software.models;

import jakarta.persistence.*;

@Entity
@Table(name = "announcement_media")
public class AnnouncementMedia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "announcement_id", nullable = false)
    private Announcement announcement;

    @Column(nullable = false)
    private String mediaType; // e.g., "image", "video", "document"

    @Column(nullable = false)
    private String mediaUrl;

    // Getters and setters
}
