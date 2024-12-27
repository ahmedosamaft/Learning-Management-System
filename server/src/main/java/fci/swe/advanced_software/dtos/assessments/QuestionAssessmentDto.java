package fci.swe.advanced_software.dtos.assessments;

import fci.swe.advanced_software.utils.validators.internal.ULID;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionAssessmentDto {

    @NotNull(message = "Question id is required.")
    @ULID(message = "Question id must be a valid UUID.")
    String questionId;

    String AssessmentId;
}
