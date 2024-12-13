package fci.swe.advanced_software.utils.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = QuestionOptionsValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidQuestionOptions {

    String message() default "Invalid options for the specified question type.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
