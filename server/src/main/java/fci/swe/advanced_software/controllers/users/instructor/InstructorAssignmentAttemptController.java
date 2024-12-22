package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.feedback.FeedbackUpdateDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
@PreAuthorize("@authorizationService.isTeaching(#course_id)")
public class InstructorAssignmentAttemptController {
    private final IAttemptService attemptService;

    @GetMapping("students/{studentId}/attempts")
    public ResponseEntity<?> getStudentAttempts(@PathVariable String course_id,
                                                @PathVariable String studentId,
                                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        return attemptService.getAttemptsByCourseIdAndStudentId(course_id, studentId, page, size);
    }

    @GetMapping("assignments/{assignmentId}/attempts")
    @PreAuthorize("@authorizationService.containsAssessment(#course_id,#assignmentId,'Assignment')")
    public ResponseEntity<?> getAssignmentAttempts(@PathVariable String course_id,
                                                   @PathVariable String assignmentId,
                                                   @RequestParam(required = false, defaultValue = "1") Integer page,
                                                   @RequestParam(required = false, defaultValue = "10") Integer size) {
        return attemptService.getAttemptsByAssessmentId(assignmentId, page, size);
    }

    @GetMapping("assignments/{assignmentId}/attempts/{attemptId}")
    @PreAuthorize("@authorizationService.containsAssessment(#course_id,#assignmentId,'Assignment')")
    public ResponseEntity<?> getAttempt(@PathVariable String course_id,
                                        @PathVariable String assignmentId,
                                        @PathVariable String attemptId) {
        return attemptService.getAttemptById(attemptId);
    }

    @PutMapping("assignments/{assignmentId}/attempts/{attemptId}")
    @PreAuthorize("@authorizationService.containsAssessment(#course_id,#assignmentId,'Assignment')")
    public ResponseEntity<?> updateAttempt(@PathVariable String course_id,
                                           @PathVariable String assignmentId,
                                           @PathVariable String attemptId,
                                           @Valid @RequestBody FeedbackUpdateDto feedbackDto) {
        return attemptService.updateAttempt(attemptId, feedbackDto);
    }
}
