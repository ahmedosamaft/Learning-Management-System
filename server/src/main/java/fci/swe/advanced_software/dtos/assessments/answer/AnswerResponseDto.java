package fci.swe.advanced_software.dtos.assessments.answer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerResponseDto {
    private String id;
    private String questionId;
    private String assessmentId;
    private String attemptId;
    private String studentId;
    private String gradingType;
    private Integer grade;
    private String answer;
}
