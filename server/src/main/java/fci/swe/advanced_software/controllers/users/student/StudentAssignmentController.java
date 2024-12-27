package fci.swe.advanced_software.controllers.users.student;

import fci.swe.advanced_software.dtos.assessments.answer.AnswerRequestDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.IAnswerService;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.utils.AuthUtils;
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
@RequestMapping(Constants.STUDENT_CONTROLLER + "/assignments")
@RequiredArgsConstructor
@RolesAllowed(Roles.STUDENT)
public class StudentAssignmentController {

    private final IAssessmentService assessmentService;
    private final IAttemptService attemptService;
    private final IAnswerService answerService;
    private final AuthUtils authUtils;

    @GetMapping
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getAssignments(@PathVariable @ULID String courseId,
                                            @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                            @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return assessmentService.getAllAssessments(courseId, AssessmentType.ASSIGNMENT, page, size);
    }

    @PostMapping("/{assignmentId}")
    @PreAuthorize("""
            @authorizationService.isEnrolled(#courseId)
            AND @authorizationService.containsAssessment(#courseId, #assignmentId, "Assignment")
            """)
    public ResponseEntity<?> startAssignmentAttempt(@PathVariable @ULID String courseId, @PathVariable @ULID String assignmentId) {
        return attemptService.createAttempt(courseId, AssessmentType.ASSIGNMENT, assignmentId);
    }

    @GetMapping("/{assignmentId}/questions")
    @PreAuthorize("""
            @authorizationService.isEnrolled(#courseId)
            AND @authorizationService.containsAssessment(#courseId, #assignmentId, "Assignment")
            """)
    public ResponseEntity<?> getAssignmentQuestions(@PathVariable @ULID String courseId,
                                                    @PathVariable @ULID String assignmentId,
                                                    @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                                    @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return assessmentService.getAssessmentQuestionsForStudent(assignmentId, AssessmentType.ASSIGNMENT, page, size);
    }

    @PostMapping("/{assignmentId}/attempts/{attemptId}")
    @PreAuthorize("""
            @authorizationService.isEnrolled(#courseId)
            AND @authorizationService.containsAssessment(#courseId, #assignmentId, "Assignment")
            """)
    public ResponseEntity<?> submitAssignmentAnswers(@PathVariable @ULID String courseId,
                                                     @PathVariable @ULID String assignmentId,
                                                     @PathVariable @ULID String attemptId,
                                                     @RequestBody @Valid List<AnswerRequestDto> answers) {

        return answerService.submitAnswers(
                courseId,
                assignmentId,
                AssessmentType.ASSIGNMENT,
                attemptId,
                answers);
    }
}
