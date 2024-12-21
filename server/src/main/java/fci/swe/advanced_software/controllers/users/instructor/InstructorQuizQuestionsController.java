package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.QuestionAssessmentDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.assessment.AssessmentService;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}/quizzes/{quiz_id}/questions")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorQuizQuestionsController {

    private final IAssessmentService assessmentService;

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable String course_id,
                                                      @PathVariable String quiz_id) {
        return assessmentService.getQuestionsOfAssessment(quiz_id);
    }

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> addQuestionsToQuiz(@PathVariable String course_id,
                                                      @PathVariable String quiz_id,
                                                      @Valid @RequestBody List<QuestionAssessmentDto> questionAssessmentDtos) {
        return assessmentService.addQuestionsToAssessment(quiz_id, questionAssessmentDtos);
    }

    @DeleteMapping("/{question_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> removeQuestionFromQuiz(@PathVariable String course_id,
                                                          @PathVariable String quiz_id,
                                                          @PathVariable String question_id) {
        return assessmentService.removeQuestionFromAssessment(quiz_id, question_id);
    }
}
