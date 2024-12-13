package fci.swe.advanced_software.utils.validators;

import fci.swe.advanced_software.dtos.assessments.question.IQuestionDto;
import fci.swe.advanced_software.models.assessments.QuestionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class QuestionOptionsValidator implements ConstraintValidator<ValidQuestionOptions, IQuestionDto> {

    @Override
    public boolean isValid(IQuestionDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        Map<String, String> options = dto.getOptions();
        if (options == null) {
            return true;
        }

        QuestionType questionType = dto.getQuestionType();
        if (questionType == null) {
            return false;
        }

        if (questionType.equals(QuestionType.MCQ)) {
            return !options.isEmpty();
        } else if (questionType.equals(QuestionType.TRUE_FALSE)) {
            return options.size() == 2;
        }
        return false;
    }
}
