package fci.swe.advanced_software.dtos.course.announcement;

import fci.swe.advanced_software.dtos.users.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnnouncementResponseDto {
    private String id;
    private String courseId;
    private UserResponseDto author;
    private String title;
    private String content;
    private Timestamp postedAt;
    private List<String> mediaUrls;
    private List<CommentDto> comments;
}
