package fci.swe.advanced_software.controllers.users.student;

import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
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
@RequestMapping(Constants.STUDENT_CONTROLLER + "/attempts")
@RequiredArgsConstructor
@RolesAllowed(Roles.STUDENT)
public class StudentAttemptController {

    private final IAttemptService attemptService;
    private final AuthUtils authUtils;

    @GetMapping
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getAttempts(@PathVariable @UUID String courseId,
                                         @RequestParam(required = false) @Normalized @Pattern(regexp = "assignment|quiz") String type,
                                         @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                         @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return attemptService.getAttemptsByCourseIdAndStudentId(courseId, authUtils.getCurrentUserId(), type, page, size);
    }

    @GetMapping("/{attemptId}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getAttempt(@PathVariable @UUID String courseId,
                                        @PathVariable @UUID String attemptId) {
        return attemptService.getAttemptByIdForStudent(courseId, attemptId);
    }

}
