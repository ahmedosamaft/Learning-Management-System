package fci.swe.advanced_software.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("STUDENT")
@Data
@EqualsAndHashCode(callSuper = true)
public class Student extends User {
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Enrollment> enrollments = new HashSet<>();
}