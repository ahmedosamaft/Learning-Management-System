package fci.swe.advanced_software.dtos.assessments.question;

import fci.swe.advanced_software.models.assessments.QuestionType;
import fci.swe.advanced_software.utils.validators.question.ValidAnswer;
import fci.swe.advanced_software.utils.validators.question.ValidQuestionOptions;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import java.util.Map;

@Data
@Builder
@ValidQuestionOptions
@ValidAnswer
public class QuestionRequestDto {

    private String courseId;

    @NotNull(message = "Question text is required.")
    @NotBlank(message = "Question text cannot be blank.")
    @Length(max = 255, message = "Question text cannot exceed 500 characters.")
    private String text;

    @URL
    private String imageUrl;

    @NotNull(message = "Correct answer is required.")
    @NotEmpty(message = "Correct answer cannot be blank.")
    private String correctAnswer;

    @NotNull(message = "Question type is required.")
    private QuestionType questionType;

    @NotNull(message = "Score is required.")
    private Integer score;

    private Map<String, String> options;
}
