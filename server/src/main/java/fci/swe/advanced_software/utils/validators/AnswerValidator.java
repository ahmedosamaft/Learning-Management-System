package fci.swe.advanced_software.utils.validators;

import fci.swe.advanced_software.dtos.assessments.question.IQuestionDto;
import fci.swe.advanced_software.models.assessments.QuestionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class AnswerValidator implements ConstraintValidator<ValidAnswer, IQuestionDto> {

    @Override
    public boolean isValid(IQuestionDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        String answerId = dto.getCorrectAnswer();
        if(answerId == null) {
            return true;
        }
        QuestionType questionType = dto.getQuestionType();
        if(questionType == null) {
            return false;
        }
        Map<String, String> options = dto.getOptions();
        if (questionType.equals(QuestionType.MCQ) || questionType.equals(QuestionType.TRUE_FALSE)) {
            return options != null && options.containsKey(answerId);
        } else if (questionType.equals(QuestionType.SHORT_ANSWER)) {
            return !answerId.isBlank();
        }

        return true;
    }
}
