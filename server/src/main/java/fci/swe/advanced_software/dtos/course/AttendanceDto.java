package fci.swe.advanced_software.dtos.course;

import fci.swe.advanced_software.utils.validators.internal.ULID;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;

import java.sql.Timestamp;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDto {
    private String id;

    @NotNull
    @ULID
    private String studentId;

    @NotNull
    @ULID
    private String lessonId;

    @NotNull
    @ULID
    private String courseId;

    @NotNull
    @Past
    private Timestamp attendedAt;
}
