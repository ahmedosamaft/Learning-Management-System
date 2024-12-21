package fci.swe.advanced_software.dtos.assessments.answer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
@Builder
public class AnswerRequestDto {
    @NotNull(message = "Question ID is required.")
    @NotBlank(message = "Question ID cannot be blank.")
    @UUID(message = "Question ID must be a valid UUID.")
    private String questionId;

    @NotNull(message = "Answer is required.")
    @NotBlank(message = "Answer cannot be blank.")
    private String answer;
}
