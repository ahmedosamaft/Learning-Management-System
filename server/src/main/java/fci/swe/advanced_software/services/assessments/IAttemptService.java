package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.dtos.assessments.feedback.FeedbackUpdateDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import org.springframework.http.ResponseEntity;

public interface IAttemptService {

    ResponseEntity<?> createAttempt(String course_id, AssessmentType type, String assessment_id);

    ResponseEntity<?> getAttemptById(String id);

    ResponseEntity<?> getAttemptsByAssessmentId(String assessmentId, Integer page, Integer size);

    ResponseEntity<?> getAttemptsByCourseIdAndStudentId(String courseId, String studentId, String type, Integer page, Integer size);

    ResponseEntity<?> updateAttempt(String id, FeedbackUpdateDto feedbackDto);

    ResponseEntity<?> getAttemptByIdForStudent(String courseId, String attemptId);
}
