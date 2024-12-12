package fci.swe.advanced_software.dtos.assessments;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Set;

@Data
@Builder
public class AttemptResponseDto {

    private String id;

    private String assessmentId;

    private String studentId;

    private Timestamp attemptedAt;

    private Set<String> submissionIds;
}
