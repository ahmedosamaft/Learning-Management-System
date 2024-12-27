package fci.swe.advanced_software.controllers.users.student;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.IAnswerService;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.validators.internal.ULID;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.STUDENT_CONTROLLER + "/quizzes")
@RequiredArgsConstructor
@RolesAllowed(Roles.STUDENT)
public class StudentQuizController {

    private final IAssessmentService assessmentService;
    private final IAnswerService answerService;
    private final IAttemptService attemptService;

    @GetMapping
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getQuizzes(@PathVariable @ULID String courseId,
                                        @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                        @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size){
        return assessmentService.getAllAssessments(courseId, AssessmentType.QUIZ, page, size);
    }

    @PostMapping("/{quizId}")
    @PreAuthorize("""
            @authorizationService.isEnrolled(#courseId)
            AND @authorizationService.containsAssessment(#courseId, #quizId, "Quiz")
            """)
    public ResponseEntity<?> startQuizAttempt(@PathVariable @ULID String courseId, @PathVariable @ULID String quizId) {
        return attemptService.createAttempt(courseId, AssessmentType.QUIZ, quizId);
    }


    @GetMapping("/{quizId}/questions")
    @PreAuthorize("""
            @authorizationService.isEnrolled(#courseId)
            AND @authorizationService.containsAssessment(#courseId, #quizId, "Quiz")
            """)
    public ResponseEntity<?> getQuizzesQuestions(@PathVariable @ULID String courseId,
                                                 @PathVariable @ULID String quizId,
                                                 @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                                 @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return assessmentService.getAssessmentQuestionsForStudent(quizId, AssessmentType.QUIZ, page, size);
    }


    @PostMapping("/{quizId}/attempts/{attemptId}")
    @PreAuthorize("""
            @authorizationService.isEnrolled(#courseId)
            AND @authorizationService.containsAssessment(#courseId, #quizId, "Quiz")
            """)
    public ResponseEntity<?> submitQuizAnswers(@PathVariable @ULID String courseId,
                                               @PathVariable @ULID String quizId,
                                               @PathVariable @ULID String attemptId,
                                               @RequestBody @Valid List<AnswerRequestDto> answers) {

        return answerService.submitAnswers(
                courseId,
                quizId,
                AssessmentType.QUIZ,
                attemptId,
                answers);
    }

}
