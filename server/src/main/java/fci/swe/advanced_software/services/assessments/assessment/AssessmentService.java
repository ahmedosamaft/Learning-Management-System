package fci.swe.advanced_software.services.assessments.assessment;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentRequestDto;
import fci.swe.advanced_software.models.courses.Course;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AssessmentService implements IAssessmentService {


    @Override
    public ResponseEntity<?> createAssessment(AssessmentRequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> updateAssessment(String id, AssessmentRequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAssessment(String id) {
        return null;
    }

    @Override
    public ResponseEntity<?> getAssessmentsByCourse(Course course) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteAssessment(String id) {
        return null;
    }
}
