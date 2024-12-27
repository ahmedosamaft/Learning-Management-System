package fci.swe.advanced_software.dtos.assessments.answer;

import fci.swe.advanced_software.utils.validators.internal.ULID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnswerRequestDto {
    @NotNull(message = "Question ID is required.")
    @NotBlank(message = "Question ID cannot be blank.")
    @ULID(message = "Question ID must be a valid UUID.")
    private String questionId;

    @NotNull(message = "Answer is required.")
    @NotBlank(message = "Answer cannot be blank.")
    private String answer;
}
