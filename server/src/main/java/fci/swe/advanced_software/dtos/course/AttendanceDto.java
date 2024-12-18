package fci.swe.advanced_software.dtos.course;

import fci.swe.advanced_software.utils.validators.lesson.ValidLesson;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

import java.sql.Timestamp;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ValidLesson
@Builder
public class AttendanceDto {
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
