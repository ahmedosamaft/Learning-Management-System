package fci.swe.advanced_software.utils.validators.question;

import fci.swe.advanced_software.dtos.assessments.question.QuestionRequestDto;
import fci.swe.advanced_software.models.assessments.QuestionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Map;

public class QuestionOptionsValidator implements ConstraintValidator<ValidQuestionOptions, QuestionRequestDto> {

    @Override
    public boolean isValid(QuestionRequestDto dto, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();

        if (dto == null) {
            return true;
        }

        Map<String, String> options = dto.getOptions();
        QuestionType questionType = dto.getQuestionType();

        if (questionType == null) {
            addConstraintViolation(context, "Question type must be specified");
            return false;
        }

        return switch (questionType) {
            case MCQ -> validateMCQOptions(options, context);
            case TRUE_FALSE -> validateTrueFalseOptions(options, context);
            case SHORT_ANSWER -> validateShortAnswerOptions(options, context);
        };
    }

    private boolean validateMCQOptions(Map<String, String> options, ConstraintValidatorContext context) {
        if (options == null || options.isEmpty()) {
            addConstraintViolation(context, "MCQ questions must have at least one option");
            return false;
        }
        return true;
    }

    private boolean validateTrueFalseOptions(Map<String, String> options, ConstraintValidatorContext context) {
        if (options == null || options.size() != 2) {
            addConstraintViolation(context, "True/False questions must have exactly two options");
            return false;
        }

        boolean hasTrue = options.containsValue("True") || options.containsValue("true");
        boolean hasFalse = options.containsValue("False") || options.containsValue("false");

        if (!hasTrue || !hasFalse) {
            addConstraintViolation(context, "True/False questions must have 'True' and 'False' options");
            return false;
        }

        return true;
    }

    private boolean validateShortAnswerOptions(Map<String, String> options, ConstraintValidatorContext context) {
        if (options != null && !options.isEmpty()) {
            addConstraintViolation(context, "Short answer questions should not have options");
            return false;
        }
        return true;
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode("options")
                .addConstraintViolation();
    }
}