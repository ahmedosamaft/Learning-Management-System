package fci.swe.advanced_software.dtos.assessments.question;

import fci.swe.advanced_software.models.assessments.QuestionType;
import fci.swe.advanced_software.utils.validators.ValidAnswer;
import fci.swe.advanced_software.utils.validators.ValidQuestionOptions;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class QuestionRequestDto implements IQuestionDto {

    @NotNull(message = "Assessment ID is required.")
    @NotBlank(message = "Assessment ID cannot be blank.")
    @UUID(message = "Assessment ID must be a valid UUID.")
    private String assessmentId;

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

    private Map<String, String> options;
}
