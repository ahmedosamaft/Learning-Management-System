package fci.swe.advanced_software.dtos.course;

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
    private String postedByUserId;
    private String title;
    private String content;
    private Timestamp postedAt;
    private List<String> mediaUrls;
    private List<String> commentIds;
}
