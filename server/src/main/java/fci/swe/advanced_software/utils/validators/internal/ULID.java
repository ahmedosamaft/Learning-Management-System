package fci.swe.advanced_software.utils.validators.internal;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = UlidValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ULID {
    String message() default "Invalid ULID format";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}