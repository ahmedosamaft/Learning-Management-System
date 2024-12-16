package fci.swe.advanced_software.dtos.course;

import fci.swe.advanced_software.utils.validators.lesson.ValidLesson;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@ValidLesson
public class LessonDto {
    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Content is required.")
    private String content;

    @NotNull(message = "Course is required.")
    private String courseId;

    private List<MediaDto> media;
}
