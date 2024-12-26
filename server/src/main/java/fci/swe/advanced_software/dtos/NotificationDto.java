package fci.swe.advanced_software.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
    @UUID
    private String id;
    private String title;
    private String content;
    private String courseId;
    private boolean isRead;
}
