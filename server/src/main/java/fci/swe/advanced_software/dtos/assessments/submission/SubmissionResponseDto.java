package fci.swe.advanced_software.dtos.assessments.submission;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubmissionResponseDto {
    private String id;
    private String questionId;
    private String assessmentId;
    private String attemptId;
    private String studentId;
    private String gradingType;
    private String answer;
}
