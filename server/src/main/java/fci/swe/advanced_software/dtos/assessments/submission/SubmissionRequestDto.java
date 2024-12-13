package fci.swe.advanced_software.dtos.assessments.submission;

import fci.swe.advanced_software.models.assessments.GradingType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

@Data
@Builder
public class SubmissionRequestDto {

    @NotNull(message = "Question ID is required.")
    @NotBlank(message = "Question ID cannot be blank.")
    @UUID(message = "Question ID must be a valid UUID.")
    private String questionId;

    @NotNull(message = "Assessment ID is required.")
    @NotBlank(message = "Assessment ID cannot be blank.")
    @UUID(message = "Assessment ID must be a valid UUID.")
    private String assessmentId;

    @NotNull(message = "Attempt ID is required.")
    @NotBlank(message = "Attempt ID cannot be blank.")
    @UUID(message = "Attempt ID must be a valid UUID.")
    private String attemptId;

    @NotNull(message = "Student ID is required.")
    @NotBlank(message = "Student ID cannot be blank.")
    @UUID(message = "Student ID must be a valid UUID.")
    private String studentId;

    @NotNull(message = "Answer is required.")
    @NotBlank(message = "Answer cannot be blank.")
    private String answer;

    @NotNull(message = "Grading type is required.")
    @NotBlank(message = "Grading type cannot be blank.")
    private GradingType gradingType;
}
