package fci.swe.advanced_software.dtos.course;

import fci.swe.advanced_software.utils.validators.internal.ULID;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentDto {
    @ULID
    @NotNull(message = "Student ID is required")
    private String studentId;
    @ULID
    @NotNull(message = "Course ID is required")
    private String courseId;

    private Integer grade;
}
