package fci.swe.advanced_software.dtos.assessments;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.sql.Timestamp;

@Data
@Builder
public class AttemptRequestDto {

    @NotBlank(message = "Assessment ID is required.")
    @Length(min = 36, max = 36, message = "Assessment ID must be 36 characters long.")
    private String assessmentId;

    @NotBlank(message = "Student ID is required.")
    @Length(min = 36, max = 36, message = "Student ID must be 36 characters long.")
    private String studentId;

    @NotNull(message = "Attempted timestamp is required.")
    @PastOrPresent(message = "Attempted timestamp cannot be in the future.")
    private Timestamp attemptedAt;
}

