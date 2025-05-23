package fci.swe.advanced_software.dtos.assessments.Attempt;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerAttemptDto;
import fci.swe.advanced_software.dtos.assessments.feedback.FeedbackDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttemptResponseDto {

    private String id;

    private UserResponseDto student;

    private FeedbackDto feedback;

    private Timestamp attemptedAt;

    private boolean isGraded;

    private List<AnswerAttemptDto> answers;
}
