package fci.swe.advanced_software.dtos.course.announcement;

import fci.swe.advanced_software.dtos.users.UserResponseDto;
import lombok.*;

import java.sql.Timestamp;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AnnouncementListDto {
    private String id;
    private UserResponseDto author;
    private String title;
    private Timestamp postedAt;
}
