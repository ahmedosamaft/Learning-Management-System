package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.services.assessments.AttemptService;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}")
@RequiredArgsConstructor
public class InstructorAttemptController {
    private final IAttemptService attemptService;

    @GetMapping("assignments/{assignmentId}/attempts")
    @PreAuthorize("@authorizationService.isTeaching(#course_id) AND @authorizationService.containsAssessment(#course_id,#assignmentId)")
    public ResponseEntity<?> getAttemptsOfAssignment(@PathVariable String course_id,
                                                     @PathVariable String assignmentId) {
        return attemptService.getAttemptsByAssessmentId(assignmentId);
    }

    @GetMapping("students/{studentId}/attempts")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getAttemptsOfStudent(@PathVariable String course_id,
                                                  @PathVariable String studentId) {
        return attemptService.getAttemptsByCourseIdAndStudentId(course_id, studentId);
    }
}
