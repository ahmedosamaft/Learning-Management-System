package fci.swe.advanced_software.dtos.course.announcement;

import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.utils.validators.lesson.ValidLesson;
import lombok.*;

import java.sql.Timestamp;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ValidLesson
@Builder
public class CommentDto {
    private String id;
    private UserResponseDto author;
    private String content;
    private Timestamp commentedAt;
}
