package fci.swe.advanced_software.dtos.assessments.feedback;

import fci.swe.advanced_software.models.assessments.FeedbackType;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {

    private String id;

    private String comments;

    private Integer grade;

    @NotNull
    @NotBlank
    @UUID(message = "Attempt ID must be a valid UUID.")
    private String attemptId;

    @NotNull
    @NotBlank
    @UUID(message = "Student ID must be a valid UUID.")
    private String studentId;

    @NotNull
    @NotBlank
    @UUID(message = "Instructor ID must be a valid UUID.")
    private String instructorId;

    @NotNull
    private FeedbackType feedbackType;

    @AssertTrue
    public boolean isCommentsValid() {
        return !(feedbackType == FeedbackType.MANUAL || !(comments == null || comments.isEmpty()));
    }
}
