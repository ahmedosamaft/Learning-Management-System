package fci.swe.advanced_software.services.assessments.assessment;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.assessments.QuestionAssessmentDto;
import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAssessmentService {
    ResponseEntity<Response> getAllAssessments(String course_id, AssessmentType type, Integer page, Integer size);

    ResponseEntity<Response> createAssessment(String courseId, AssessmentType assessmentType, AssessmentDto requestDto);

    ResponseEntity<Response> updateAssessment(String id, AssessmentType type, AssessmentDto requestDto);

    ResponseEntity<Response> getAssessment(String id);

    ResponseEntity<Response> deleteAssessment(String id);

    ResponseEntity<Response> addQuestionsToAssessment(String assessmentId, List<QuestionAssessmentDto> questionAssessmentDtos);

    ResponseEntity<Response> removeQuestionFromAssessment(String assessmentId, String questionId);

    ResponseEntity<Response> getAssessmentQuestions(String assessmentId, Integer page, Integer size);

    ResponseEntity<Response> getAssessmentQuestionsForStudent(String assessmentId, AssessmentType assessmentType, Integer page, Integer size);
}


