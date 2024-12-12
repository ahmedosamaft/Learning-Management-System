package fci.swe.advanced_software.dtos.assessments;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentResponseDto {
    private String id;
    private String instructions;
    private String courseId;
    private Integer maxScore;
    private Timestamp startsAt;
    private Timestamp endsAt;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
