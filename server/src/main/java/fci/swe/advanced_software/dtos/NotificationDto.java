package fci.swe.advanced_software.dtos;

import fci.swe.advanced_software.utils.validators.internal.ULID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationDto {
    @ULID
    private String id;
    private String title;
    private String content;
    private String courseId;
    private boolean isRead;
}
