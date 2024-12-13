package fci.swe.advanced_software.utils.validators;

import fci.swe.advanced_software.dtos.assessments.QuestionRequestDto;
import fci.swe.advanced_software.models.assessments.QuestionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class AnswerValidator implements ConstraintValidator<ValidAnswer, QuestionRequestDto> {

    @Override
    public boolean isValid(QuestionRequestDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        String answerId = dto.getCorrectAnswer();
        QuestionType questionType = dto.getQuestionType();
        Map<String, String> options = dto.getOptions();

        if (questionType.equals(QuestionType.MCQ) || questionType.equals(QuestionType.TRUE_FALSE)) {
            return options != null && options.containsKey(answerId);
        } else if (questionType.equals(QuestionType.SHORT_ANSWER)) {
            return answerId != null && !answerId.trim().isEmpty();
        }

        return true;
    }
}
