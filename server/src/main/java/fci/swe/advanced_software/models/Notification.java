package fci.swe.advanced_software.models;

import fci.swe.advanced_software.models.users.AbstractUser;
import jakarta.persistence.*;

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
    private AbstractUser recipient;
}
