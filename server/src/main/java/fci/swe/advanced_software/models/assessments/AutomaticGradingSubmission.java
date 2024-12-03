package fci.swe.advanced_software.models.assessments;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("AUTOMATIC_GRADING")
public class AutomaticGradingSubmission extends Submission {
}
