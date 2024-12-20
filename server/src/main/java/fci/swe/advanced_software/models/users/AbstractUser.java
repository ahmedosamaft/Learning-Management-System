package fci.swe.advanced_software.models.users;

import fci.swe.advanced_software.models.AbstractEntity;
import fci.swe.advanced_software.models.Notification;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;
import java.util.TreeSet;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AbstractUser extends AbstractEntity {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private Role role;

    @OneToMany(mappedBy = "recipient", cascade = CascadeType.ALL)
    @Column(nullable = false)
    private Set<Notification> notifications = new TreeSet<>();
}
