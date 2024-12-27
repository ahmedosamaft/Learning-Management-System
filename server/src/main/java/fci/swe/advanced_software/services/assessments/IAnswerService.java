package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAnswerService {
    ResponseEntity<?> submitAnswers(String course_id,
                                    String assessment_id,
                                    AssessmentType type,
                                    String attempt_id,
                                    List<AnswerRequestDto> answers
    );
}
