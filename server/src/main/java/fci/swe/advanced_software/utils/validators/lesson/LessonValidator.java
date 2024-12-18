package fci.swe.advanced_software.utils.validators.lesson;

import fci.swe.advanced_software.dtos.course.LessonDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class LessonValidator implements ConstraintValidator<ValidLesson, LessonDto> {

    @Override
    public boolean isValid(LessonDto lessonDto, ConstraintValidatorContext context) {
        if (lessonDto == null) {
            return true;
        }

        boolean isValid = true;

        // Validate title
        if (lessonDto.getTitle() == null || lessonDto.getTitle().isBlank()) {
            context.buildConstraintViolationWithTemplate("Title is required.")
                    .addPropertyNode("title")
                    .addConstraintViolation();
            isValid = false;
        } else if (lessonDto.getTitle().length() > 100) {
            context.buildConstraintViolationWithTemplate("Title must not exceed 100 characters.")
                    .addPropertyNode("title")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validate content
        if (lessonDto.getContent() == null || lessonDto.getContent().isBlank()) {
            context.buildConstraintViolationWithTemplate("Content is required.")
                    .addPropertyNode("content")
                    .addConstraintViolation();
            isValid = false;
        } else if (lessonDto.getContent().length() > 1000) {
            context.buildConstraintViolationWithTemplate("Content must not exceed 1000 characters.")
                    .addPropertyNode("content")
                    .addConstraintViolation();
            isValid = false;
        }

        // Validate courseId
        if (lessonDto.getCourseId() == null) {
            context.buildConstraintViolationWithTemplate("Course is required.")
                    .addPropertyNode("courseId")
                    .addConstraintViolation();
            isValid = false;
        }

        context.disableDefaultConstraintViolation();
        return isValid;
    }
}
