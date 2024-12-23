package fci.swe.advanced_software.dtos.course.announcement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnnouncementRequestDto {

    private String courseId;

    private String userId;

    @NotNull(message = "Title is required.")
    @NotBlank(message = "Title cannot be blank.")
    private String title;

    @NotNull(message = "Content is required.")
    @NotBlank(message = "Content cannot be blank.")
    private String content;
}
