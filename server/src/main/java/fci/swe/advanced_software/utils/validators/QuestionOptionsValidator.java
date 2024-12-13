package fci.swe.advanced_software.utils.validators;

import fci.swe.advanced_software.dtos.assessments.QuestionRequestDto;
import fci.swe.advanced_software.models.assessments.QuestionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class QuestionOptionsValidator implements ConstraintValidator<ValidQuestionOptions, QuestionRequestDto> {

    @Override
    public boolean isValid(QuestionRequestDto dto, ConstraintValidatorContext context) {
        if (dto == null) {
            return true;
        }

        QuestionType questionType = dto.getQuestionType();
        Map<String, String> options = dto.getOptions();
        System.out.println("QuestionType: " + questionType);
        System.out.println("Options: " + options);
        if (questionType.equals(QuestionType.MCQ)) {
            return options != null && !options.isEmpty();
        } else if (questionType.equals(QuestionType.TRUE_FALSE)) {
            return options != null && options.size() == 2;
        } else if (questionType.equals(QuestionType.SHORT_ANSWER)) {
            return options == null || options.isEmpty();
        }

        return false;
    }
}
