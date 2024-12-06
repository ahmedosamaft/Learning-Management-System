package fci.swe.advanced_software.models.users;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue("ADMIN")
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class Admin extends AbstractUser {
}
