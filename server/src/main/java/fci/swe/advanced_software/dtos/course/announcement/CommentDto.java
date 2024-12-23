package fci.swe.advanced_software.dtos.course.announcement;

import fci.swe.advanced_software.dtos.users.UserResponseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotNull(message = "Content is required.")
    @NotBlank(message = "Content is required.")
    private String content;
    private Timestamp commentedAt;
}
