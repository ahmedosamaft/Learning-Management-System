package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.feedback.FeedbackUpdateDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Normalized;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorAssignmentAttemptController {
    private final IAttemptService attemptService;

    @GetMapping("students/{studentId}/attempts")
    @PreAuthorize(""" 
                       @authorizationService.isTeaching(#courseId)
                       AND @authorizationService.isEnrolled(#courseId)
            """)
    public ResponseEntity<?> getStudentAttempts(@PathVariable @UUID String courseId,
                                                @PathVariable @UUID String studentId,
                                                @RequestParam(required = false) @Normalized @Pattern(regexp = "assignment|quiz") String type,
                                                @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                                @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return attemptService.getAttemptsByCourseIdAndStudentId(courseId, studentId, type, page, size);
    }

    @GetMapping("assignments/{assignmentId}/attempts")
    @PreAuthorize(""" 
                       @authorizationService.containsAssessment(#courseId,#assignmentId,'Assignment')
                       AND @authorizationService.isTeaching(#courseId)
                       AND @authorizationService.isSameType(#assignmentId,'Assignment')
            """)
    public ResponseEntity<?> getAssignmentAttempts(@PathVariable @UUID String courseId,
                                                   @PathVariable @UUID String assignmentId,
                                                   @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                                   @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return attemptService.getAttemptsByAssessmentId(assignmentId, page, size);
    }

    @GetMapping("assignments/{assignmentId}/attempts/{attemptId}")
    @PreAuthorize(""" 
                       @authorizationService.containsAssessment(#courseId,#assignmentId,'Assignment')
                       AND @authorizationService.isTeaching(#courseId)
                       AND @authorizationService.isSameType(#assignmentId,'Assignment')
                       AND @authorizationService.containsAttempt(#assignmentId,#attemptId)
            """)
    public ResponseEntity<?> getAttempt(@PathVariable @UUID String courseId,
                                        @PathVariable @UUID String assignmentId,
                                        @PathVariable @UUID String attemptId) {
        return attemptService.getAttemptById(attemptId);
    }

    @PutMapping("assignments/{assignmentId}/attempts/{attemptId}")
    @PreAuthorize(""" 
                       @authorizationService.containsAssessment(#courseId,#assignmentId,'Assignment')
                       AND @authorizationService.isTeaching(#courseId)
                       AND @authorizationService.isSameType(#assignmentId,'Assignment')
                       AND @authorizationService.containsAttempt(#assignmentId,#attemptId)
            """)
    public ResponseEntity<?> updateAttempt(@PathVariable @UUID String courseId,
                                           @PathVariable @UUID String assignmentId,
                                           @PathVariable @UUID String attemptId,
                                           @Valid @RequestBody FeedbackUpdateDto feedbackDto) {
        return attemptService.updateAttempt(attemptId, feedbackDto);
    }
}
