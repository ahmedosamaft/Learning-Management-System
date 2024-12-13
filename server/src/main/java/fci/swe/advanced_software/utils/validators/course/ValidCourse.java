package fci.swe.advanced_software.utils.validators.course;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CourseValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCourse {

    String message() default "Invalid course details.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
