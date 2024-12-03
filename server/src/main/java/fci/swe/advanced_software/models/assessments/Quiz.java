package fci.swe.advanced_software.models.assessments;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("QUIZ")
public class Quiz extends Assessment {
}
