package fci.swe.advanced_software.dtos.course;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnrollmentDto {
    @UUID
    @NotNull(message = "Student ID is required")
    private String studentId;
    @UUID
    @NotNull(message = "Course ID is required")
    private String courseId;

    private Integer grade;
}
