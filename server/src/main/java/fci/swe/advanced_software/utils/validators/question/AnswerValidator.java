package fci.swe.advanced_software.utils.validators.question;

import fci.swe.advanced_software.dtos.assessments.question.IQuestionDto;
import fci.swe.advanced_software.models.assessments.QuestionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.Map;

public class AnswerValidator implements ConstraintValidator<ValidAnswer, IQuestionDto> {

    @Override
    public boolean isValid(IQuestionDto dto, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (dto == null) {
            return true;
        }

        String answerId = dto.getCorrectAnswer();
        QuestionType questionType = dto.getQuestionType();
        Map<String, String> options = dto.getOptions();

        if (questionType == null) {
            addConstraintViolation(context, "Question type must be specified");
            return false;
        }

        if (!StringUtils.hasText(answerId)) {
            addConstraintViolation(context, "Correct answer cannot be null or empty");
            return false;
        }

        switch (questionType) {
            case MCQ:
            case TRUE_FALSE:
                if (options == null || !options.containsKey(answerId)) {
                    addConstraintViolation(context,
                            String.format("Invalid answer for %s. Answer must be one of the provided options.",
                                    questionType));
                    return false;
                }
                break;
            case SHORT_ANSWER:
                if (answerId.isBlank()) {
                    addConstraintViolation(context, "Short answer cannot be blank");
                    return false;
                }
                break;
            default:
                addConstraintViolation(context, "Unsupported question type");
                return false;
        }

        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode("correctAnswer")
                .addConstraintViolation();
    }
}