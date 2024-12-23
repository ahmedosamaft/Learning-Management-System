package fci.swe.advanced_software.dtos.course;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.sql.Timestamp;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDto {
    private String id;

    @NotNull
    @UUID
    private String studentId;

    @NotNull
    @UUID
    private String lessonId;

    @NotNull
    @UUID
    private String courseId;

    @NotNull
    @Past
    private Timestamp attendedAt;
}
