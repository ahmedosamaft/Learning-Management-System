package fci.swe.advanced_software.dtos.assessments.feedback;

import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.assessments.FeedbackType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackDto {

    private String id;

    private String comments;

    private Integer grade;

    private UserResponseDto instructor;

    @NotNull
    private FeedbackType feedbackType;
}
