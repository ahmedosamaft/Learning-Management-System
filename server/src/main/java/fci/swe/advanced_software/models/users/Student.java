package fci.swe.advanced_software.models.users;

import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.assessments.Submission;
import fci.swe.advanced_software.models.courses.AnnouncementComment;
import fci.swe.advanced_software.models.courses.Attendance;
import fci.swe.advanced_software.models.courses.Enrollment;
import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("STUDENT")
public class Student extends AbstractUser {
    @OneToMany(mappedBy = "student_id", cascade = CascadeType.ALL)
    private Set<Enrollment> enrollments = new HashSet<>();

    @OneToMany(mappedBy = "student_id", cascade = CascadeType.ALL)
    private Set<AnnouncementComment> comments = new HashSet<>();

    @OneToMany(mappedBy = "student_id", cascade = CascadeType.ALL)
    private Set<Attendance> attendances = new HashSet<>();

    @OneToMany(mappedBy = "student_id", cascade = CascadeType.ALL)
    private Set<Submission> submissions = new HashSet<>();

    @OneToMany(mappedBy = "student_id")
    private Set<Attempt> attempts = new HashSet<>();


}