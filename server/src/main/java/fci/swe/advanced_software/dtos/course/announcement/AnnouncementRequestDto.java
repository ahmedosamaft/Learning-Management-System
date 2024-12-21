package fci.swe.advanced_software.dtos.course.announcement;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.UUID;

import java.sql.Timestamp;

@Data
@Builder
public class AnnouncementRequestDto {

    @NotNull(message = "Course ID is required.")
    @NotBlank(message = "Course ID cannot be blank.")
    @UUID(message = "Course ID must be a valid UUID.")
    private String courseId;

    @NotNull(message = "Posted by user ID is required.")
    @NotBlank(message = "Posted by user ID cannot be blank.")
    @UUID(message = "Posted by user ID must be a valid UUID.")
    private String userId;

    @NotNull(message = "Title is required.")
    @NotBlank(message = "Title cannot be blank.")
    private String title;

    @NotNull(message = "Content is required.")
    @NotBlank(message = "Content cannot be blank.")
    private String content;

    @NotNull(message = "Posted timestamp is required.")
    private Timestamp postedAt;
}
