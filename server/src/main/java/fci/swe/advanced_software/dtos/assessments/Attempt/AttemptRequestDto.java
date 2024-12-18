package fci.swe.advanced_software.dtos.assessments.Attempt;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
public class AttemptRequestDto {

    @NotBlank(message = "Student ID is required.")
    @Length(min = 36, max = 36, message = "Student ID must be 36 characters long.")
    private String studentId;

    @NotBlank(message = "Assessment ID is required.")
    @Length(min = 36, max = 36, message = "Assessment ID must be 36 characters long.")
    private String assessmentId;

    @NotNull(message = "Score is required.")
    @Positive(message = "Score must be a positive number.")
    private Integer score;

    @NotNull(message = "Submission date is required.")
    @FutureOrPresent(message = "Submission date must be in the present or future.")
    private Timestamp submissionDate;

    @AssertTrue(message = "Score must be within the valid range of the assessment.")
    private boolean isScoreValid() {
        return score != null && score >= 0;
    }
}
