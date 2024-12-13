package fci.swe.advanced_software.utils.validators.course;

import fci.swe.advanced_software.dtos.course.CourseDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CourseValidator implements ConstraintValidator<ValidCourse, CourseDto> {

    @Override
    public boolean isValid(CourseDto courseDto, ConstraintValidatorContext context) {
        if (courseDto == null) {
            return true; // Let @NotNull handle null cases if needed
        }

        boolean isValid = true;

        // Validate code: must not be blank and size between 3 and 10
        if (courseDto.getCode() == null || courseDto.getCode().isBlank() ||
                courseDto.getCode().length() < 3 || courseDto.getCode().length() > 10) {
            context.buildConstraintViolationWithTemplate("Course code must be between 3 and 10 characters and not blank.")
                    .addPropertyNode("code")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validate name: must not be blank and size <= 100
        if (courseDto.getName() == null || courseDto.getName().isBlank() ||
                courseDto.getName().length() > 100) {
            context.buildConstraintViolationWithTemplate("Course name must not be blank and less than or equal to 100 characters.")
                    .addPropertyNode("name")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validate description: size <= 255
        if (courseDto.getDescription() != null && courseDto.getDescription().length() > 255) {
            context.buildConstraintViolationWithTemplate("Description must be less than or equal to 255 characters.")
                    .addPropertyNode("description")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validate instructorId: must not be blank
        if (courseDto.getInstructorId() == null || courseDto.getInstructorId().isBlank()) {
            context.buildConstraintViolationWithTemplate("Instructor ID is required.")
                    .addPropertyNode("instructorId")
                    .addConstraintViolation();
            isValid = false;
        }

        // Disable default constraint violation to avoid duplicates
        context.disableDefaultConstraintViolation();

        return isValid;
    }
}