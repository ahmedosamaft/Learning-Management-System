package fci.swe.advanced_software.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification extends AbstractEntity {
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isRead;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User recipient;
}
