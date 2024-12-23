package fci.swe.advanced_software.dtos.course.announcement;

import fci.swe.advanced_software.dtos.users.UserResponseDto;
import lombok.*;

import java.sql.Timestamp;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private String id;
    private UserResponseDto author;
    private String content;
    private Timestamp commentedAt;
}
