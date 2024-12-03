package fci.swe.advanced_software.models.assessments;

import fci.swe.advanced_software.models.courses.Media;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("ASSIGNMENT")
public class Assignment extends Assessment {
    @OneToMany(mappedBy = "assignment_id")
    private Set<Media> media = new HashSet<>();
}
