package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}")
@RequiredArgsConstructor
public class InstructorAttemptController {
    private final IAttemptService attemptService;

    @GetMapping("assignments/{assignmentId}/attempts")
    @PreAuthorize("@authorizationService.isTeaching(#course_id) AND @authorizationService.containsAssessment(#course_id,#assignmentId)")
    public ResponseEntity<?> getAssignmentAttempts(@PathVariable String course_id,
                                                   @PathVariable String assignmentId,
                                                   @RequestParam(required = false, defaultValue = "1") Integer page,
                                                   @RequestParam(required = false, defaultValue = "10") Integer size) {
        return attemptService.getAttemptsByAssessmentId(assignmentId, page, size);
    }

    @GetMapping("students/{studentId}/attempts")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getStudentAttempts(@PathVariable String course_id,
                                                @PathVariable String studentId,
                                                @RequestParam(required = false, defaultValue = "1") Integer page,
                                                @RequestParam(required = false, defaultValue = "10") Integer size) {
        return attemptService.getAttemptsByCourseIdAndStudentId(course_id, studentId, page, size);
    }
}
