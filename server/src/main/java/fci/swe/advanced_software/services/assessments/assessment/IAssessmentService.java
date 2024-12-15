package fci.swe.advanced_software.services.assessments.assessment;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import org.springframework.http.ResponseEntity;

public interface IAssessmentService {
    ResponseEntity<?> createAssessment(AssessmentDto requestDto);

    ResponseEntity<?> updateAssessment(String id, AssessmentDto requestDto);

    ResponseEntity<?> getAssessment(String id);

    ResponseEntity<?> deleteAssessment(String id);
}
