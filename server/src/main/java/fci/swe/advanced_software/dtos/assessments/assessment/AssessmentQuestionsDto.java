package fci.swe.advanced_software.dtos.assessments.assessment;

import fci.swe.advanced_software.dtos.assessments.question.QuestionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssessmentQuestionsDto {
    private String id;
    private String instructions;
    private String courseId;
    private Integer maxScore;
    private Timestamp startsAt;
    private Timestamp endsAt;
    private List<QuestionResponseDto> questions;
}
