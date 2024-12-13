package fci.swe.advanced_software.utils.validators.question;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = AnswerValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAnswer {

    String message() default "Invalid answer for the specified question type.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
