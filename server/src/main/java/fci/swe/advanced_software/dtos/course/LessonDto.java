package fci.swe.advanced_software.dtos.course;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.utils.validators.lesson.ValidLesson;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ValidLesson
public class LessonDto {
    private String id;

    @NotBlank(message = "Title is required.")
    private String title;

    @NotBlank(message = "Content is required.")
    private String content;

    @NotNull(message = "Course is required.")
    private String courseId;

    private List<MediaDto> media;
}
