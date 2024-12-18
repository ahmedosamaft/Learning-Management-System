package fci.swe.advanced_software.utils.validators.lesson;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LessonValidator.class)
public @interface ValidLesson {
    String message() default "Invalid lesson details";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
