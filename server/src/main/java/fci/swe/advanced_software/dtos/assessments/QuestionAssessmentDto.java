package fci.swe.advanced_software.dtos.assessments;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
@Builder
public class QuestionAssessmentDto {

    @NotNull(message = "Question id is required.")
    @UUID(message = "Question id must be a valid UUID.")
    String questionId;

    String AssessmentId;
}
