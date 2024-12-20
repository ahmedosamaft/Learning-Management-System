package fci.swe.advanced_software.services.assessments;

import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.users.Student;
import org.springframework.http.ResponseEntity;

public interface IAttemptService {

    ResponseEntity<?> createAttempt(String course_id, AssessmentType type, String assessment_id);

    ResponseEntity<?> getAttemptById(String id);

    ResponseEntity<?> getAttemptsByAssessment(Assessment assessment);

    ResponseEntity<?> getAttemptsByStudent(Student student);

    ResponseEntity<?> deleteAttempt(String id);
}
