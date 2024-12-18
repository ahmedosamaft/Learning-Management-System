package fci.swe.advanced_software.dtos.assessments.question;

import fci.swe.advanced_software.models.assessments.QuestionType;
import fci.swe.advanced_software.utils.validators.question.ValidAnswer;
import fci.swe.advanced_software.utils.validators.question.ValidQuestionOptions;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.UUID;

import java.util.Map;

@Data
@Builder
@ValidQuestionOptions
@ValidAnswer
public class QuestionUpdateDto implements IQuestionDto {

    private String id;

    @UUID(message = "Course ID must be a valid UUID.")
    private String courseId;

    @Length(max = 255, message = "Question text cannot exceed 500 characters.")
    private String text;

    @URL
    private String imageUrl;

    private String correctAnswer;

    private QuestionType questionType;

    private Integer score;

    private Map<String, String> options;
}
