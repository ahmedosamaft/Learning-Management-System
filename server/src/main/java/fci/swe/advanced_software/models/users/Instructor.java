package fci.swe.advanced_software.models.users;

import fci.swe.advanced_software.models.courses.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@DiscriminatorValue("INSTRUCTOR")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Instructor extends AbstractUser {
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private Set<Course> courses;
}
