package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.QuestionAssessmentDto;
import fci.swe.advanced_software.models.users.Roles;
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
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/quizzes/{quizId}/questions")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorQuizQuestionsController {

    private final IAssessmentService assessmentService;

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getQuestionsOfQuiz(@PathVariable String courseId,
                                                @PathVariable String quizId,
                                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        return assessmentService.getAssessmentQuestions(quizId, page, size);
    }

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> addQuestionsToQuiz(@PathVariable String courseId,
                                                      @PathVariable String quizId,
                                                      @Valid @RequestBody List<QuestionAssessmentDto> questionAssessmentDtos) {
        return assessmentService.addQuestionsToAssessment(quizId, questionAssessmentDtos);
    }

    @DeleteMapping("/{questionId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> removeQuestionFromQuiz(@PathVariable String courseId,
                                                          @PathVariable String quizId,
                                                          @PathVariable String questionId) {
        return assessmentService.removeQuestionFromAssessment(quizId, questionId);
    }
}
