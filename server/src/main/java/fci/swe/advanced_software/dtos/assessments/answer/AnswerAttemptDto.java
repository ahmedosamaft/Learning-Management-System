package fci.swe.advanced_software.dtos.assessments.answer;

import fci.swe.advanced_software.dtos.assessments.question.QuestionResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerAttemptDto {
    private QuestionResponseDto question;
    private String answer;
    private Integer grade;
}
