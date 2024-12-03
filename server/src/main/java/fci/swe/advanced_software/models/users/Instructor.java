package fci.swe.advanced_software.models.users;

import fci.swe.advanced_software.models.courses.Course;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("INSTRUCTOR")
@Data
public class Instructor extends AbstractUser {
    @OneToMany(mappedBy = "instructor_id", cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();
}
