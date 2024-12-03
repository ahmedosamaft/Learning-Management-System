package fci.swe.advanced_software.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("INSTRUCTOR")
@Data
@EqualsAndHashCode(callSuper = true)
public class Instructor extends User {
    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();
}
