package fci.swe.advanced_software.services.assessments.assessment;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import org.springframework.http.ResponseEntity;

public interface IAssessmentService {
    ResponseEntity<?> getAllAssessments(String course_id, AssessmentType type, Integer page, Integer size);

    ResponseEntity<?> createAssessment(AssessmentDto requestDto);

    ResponseEntity<?> updateAssessment(String id, AssessmentDto requestDto);

    ResponseEntity<?> getAssessment(String id);

    ResponseEntity<?> deleteAssessment(String id);
}
