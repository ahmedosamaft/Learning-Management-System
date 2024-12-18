package fci.swe.advanced_software.dtos.assessments.Attempt;

import fci.swe.advanced_software.models.assessments.Feedback;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttemptResponseDto {

    private String id;

    private String assessmentId;

    private String studentId;

    private Feedback feedback;

    private Timestamp attemptedAt;

    private Set<String> answerIds;
}
