package fci.swe.advanced_software.dtos.assessments.question;

import fci.swe.advanced_software.models.assessments.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionResponseDto {
    private String id;
    private String assessmentId;
    private String text;
    private String imageUrl;
    private String correctAnswer;
    private QuestionType questionType;
    private Map<String, String> options;
}
