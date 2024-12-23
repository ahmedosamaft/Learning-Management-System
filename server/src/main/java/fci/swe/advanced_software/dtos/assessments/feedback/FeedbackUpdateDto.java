package fci.swe.advanced_software.dtos.assessments.feedback;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FeedbackUpdateDto {
    @NotNull(message = "Comments is required")
    @NotBlank(message = "Comments is required")
    private String comments;

    @NotNull(message = "Grade is required")
    private Integer grade;
}
