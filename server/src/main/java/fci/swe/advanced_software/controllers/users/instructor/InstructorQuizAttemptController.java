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
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/quizzes/{quizId}/attempts")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorQuizAttemptController {
    private final IAttemptService attemptService;

    @GetMapping
    @PreAuthorize(""" 
                       @authorizationService.containsAssessment(#courseId,#quizId,'Quiz')
                       AND @authorizationService.isTeaching(#courseId)
                       AND @authorizationService.isSameType(#quizId,'Quiz')
            """)
    public ResponseEntity<?> getQuizAttempts(@PathVariable String courseId,
                                             @PathVariable String quizId,
                                             @RequestParam(required = false, defaultValue = "1") Integer page,
                                             @RequestParam(required = false, defaultValue = "10") Integer size) {
        return attemptService.getAttemptsByAssessmentId(quizId, page, size);
    }

    @GetMapping("/{attemptId}")
    @PreAuthorize(""" 
                       @authorizationService.containsAssessment(#courseId,#quizId,'Quiz')
                       AND @authorizationService.isTeaching(#courseId)
                       AND @authorizationService.isSameType(#quizId,'Quiz')
                       AND @authorizationService.containsAttempt(#quizId,#attemptId)
            """)
    public ResponseEntity<?> getAttempt(@PathVariable String courseId,
                                        @PathVariable String quizId,
                                        @PathVariable String attemptId) {
        return attemptService.getAttemptById(attemptId);
    }

    @PutMapping("/{attemptId}")
    @PreAuthorize(""" 
                       @authorizationService.containsAssessment(#courseId,#quizId,'Quiz')
                       AND @authorizationService.isTeaching(#courseId)
                       AND @authorizationService.isSameType(#quizId,'Quiz')
                       AND @authorizationService.containsAttempt(#quizId,#attemptId)
            """)
    public ResponseEntity<?> updateAttempt(@PathVariable String courseId,
                                           @PathVariable String quizId,
                                           @PathVariable String attemptId,
                                           @Valid @RequestBody FeedbackUpdateDto feedbackDto) {
        return attemptService.updateAttempt(attemptId, feedbackDto);
    }
}
