package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.models.assessments.AssessmentType;
import org.springframework.http.ResponseEntity;

public interface IAttemptService {

    ResponseEntity<?> createAttempt(String course_id, AssessmentType type, String assessment_id);

    ResponseEntity<?> getAttemptById(String id);

    ResponseEntity<?> getAttemptsByAssessmentId(String assessmentId, Integer page, Integer size);

    ResponseEntity<?> getAttemptsByStudentId(String studentId);

    ResponseEntity<?> getAttemptsByCourseIdAndStudentId(String courseId, String studentId, Integer page, Integer size);

    ResponseEntity<?> deleteAttempt(String id);
}
