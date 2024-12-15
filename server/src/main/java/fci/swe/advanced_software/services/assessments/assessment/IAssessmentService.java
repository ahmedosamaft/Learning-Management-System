package fci.swe.advanced_software.services.assessments.assessment;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentRequestDto;
import fci.swe.advanced_software.models.courses.Course;
import org.springframework.http.ResponseEntity;

public interface IAssessmentService {
    ResponseEntity<?> createAssessment(AssessmentRequestDto requestDto);

    ResponseEntity<?> updateAssessment(String id, AssessmentRequestDto requestDto);

    ResponseEntity<?> getAssessment(String id);

    ResponseEntity<?> getAssessmentsByCourse(Course course);

    ResponseEntity<?> deleteAssessment(String id);
}
