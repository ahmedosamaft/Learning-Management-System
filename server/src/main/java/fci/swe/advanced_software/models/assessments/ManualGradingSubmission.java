package fci.swe.advanced_software.models.assessments;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MANUAL_GRADING")
public class ManualGradingSubmission extends Submission {
}
