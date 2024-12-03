package fci.swe.advanced_software.models.users;

import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.Notification;
import jakarta.persistence.*;

import java.util.Set;
import java.util.TreeSet;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
public abstract class AbstractUser extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "recipient")
    @Column(nullable = false)
    private Set<Notification> notifications = new TreeSet<>();
}
