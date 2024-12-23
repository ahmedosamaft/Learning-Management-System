package fci.swe.advanced_software.dtos.course;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

import java.util.List;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LessonDto {

    @UUID
    private String id;

    @NotNull
    @NotBlank(message = "Title is required.")
    private String title;

    @NotNull
    @NotBlank(message = "Content is required.")
    private String content;

    private String courseId;

    private List<MediaDto> media;
}
