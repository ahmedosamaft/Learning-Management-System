package fci.swe.advanced_software.dtos.assessments.assessment;

import fci.swe.advanced_software.models.assessments.AssessmentType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentRequestDto {
    private String id;

    @NotBlank(message = "Instructions cannot be blank.")
    @Length(max = 255, message = "Instructions cannot exceed 500 characters.")
    private String instructions;

    @NotBlank(message = "Course ID is required.")
    @Length(min = 36, max = 36, message = "Course ID must be 24 characters long.")
    private String courseId;

    @NotNull(message = "Max score is required.")
    @Positive(message = "Max score must be a positive number.")
    private Integer maxScore;

    @NotNull(message = "Assessment type is required.")
    private AssessmentType type;

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
}
