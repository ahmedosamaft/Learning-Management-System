package fci.swe.advanced_software.models.users;

import fci.swe.advanced_software.models.assessments.Answer;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.courses.Attendance;
import fci.swe.advanced_software.models.courses.Enrollment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("STUDENT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Student extends AbstractUser {
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private Set<Answer> answers = new HashSet<>();

    @OneToMany(mappedBy = "student")
    private Set<Attempt> attempts = new HashSet<>();

}