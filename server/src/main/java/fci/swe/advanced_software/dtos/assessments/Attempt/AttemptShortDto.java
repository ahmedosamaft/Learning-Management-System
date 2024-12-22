package fci.swe.advanced_software.dtos.assessments.Attempt;

import fci.swe.advanced_software.dtos.users.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttemptShortDto {

    private String id;

    private UserResponseDto student;

    private boolean isGraded;

    private Timestamp attemptedAt;
}
