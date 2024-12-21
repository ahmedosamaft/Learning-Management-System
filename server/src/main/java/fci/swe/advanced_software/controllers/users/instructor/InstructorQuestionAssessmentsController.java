package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.QuestionAssessmentDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.QuestionService;
import fci.swe.advanced_software.services.assessments.assessment.AssessmentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}/assignments/{assignment_id}/questions")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorQuestionAssessmentsController {

    private final AssessmentService assessmentService;
    private final QuestionService questionService;

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getQuestionsOfAssignment(@PathVariable String course_id,
                                                      @PathVariable String assignment_id) {
        return assessmentService.getQuestionsOfAssessment(assignment_id);
    }

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> addQuestionsToAssignment(@PathVariable String course_id,
                                                      @PathVariable String assignment_id,
                                                      @Valid @RequestBody List<QuestionAssessmentDto> questionAssessmentDtos) {
        return assessmentService.addQuestionsToAssessment(assignment_id, questionAssessmentDtos);
    }

    @DeleteMapping("/{question_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> removeQuestionFromAssignment(@PathVariable String course_id,
                                                          @PathVariable String assignment_id,
                                                          @PathVariable String question_id) {
        return assessmentService.removeQuestionFromAssessment(assignment_id, question_id);
    }
}
