package fci.swe.advanced_software.models.assessments;

import fci.swe.advanced_software.models.courses.Media;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("ASSIGNMENT")
public class Assignment extends Assessment {
    @OneToMany(mappedBy = "assignment")
    private Set<Media> media = new HashSet<>();
}
