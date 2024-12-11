package fci.swe.advanced_software.dtos.assessments;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
public class AssignmentRequestDto {

    @NotBlank(message = "Instructions cannot be blank.")
    @Size(max = 500, message = "Instructions cannot exceed 500 characters.")
    private String instructions;

    @NotBlank(message = "Course ID is required.")
    private String courseId;

    @NotNull(message = "Max score is required.")
    @Positive(message = "Max score must be a positive number.")
    private Integer maxScore;

    @NotNull(message = "Start time is required.")
    @Future(message = "Start time must be in the future.")
    private Timestamp startsAt;

    @NotNull(message = "End time is required.")
    @Future(message = "End time must be in the future.")
    private Timestamp endsAt;

    @AssertTrue(message = "End time must be after the start time.")
    private boolean isEndAfterStart() {
        return endsAt != null && startsAt != null && endsAt.after(startsAt);
    }

    @NotNull(message = "Submission limit is required.")
    @Min(value = 1, message = "Submission limit must be at least 1.")
    @Max(value = 10, message = "Submission limit cannot exceed 10.")
    private Integer submissionLimit;

    @NotNull(message = "Manual grading flag is required.")
    private Boolean manualGrading;
}
