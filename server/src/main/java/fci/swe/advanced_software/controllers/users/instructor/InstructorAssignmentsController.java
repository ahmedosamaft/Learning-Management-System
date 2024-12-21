package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}/assignments")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorAssignmentsController {
    private final IAssessmentService assignmentService;

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getAllAssignments(@PathVariable String course_id, @RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        return assignmentService.getAllAssessments(course_id, AssessmentType.ASSIGNMENT, page, size);
    }

    @GetMapping("/{assignment_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getAssignmentById(@PathVariable String course_id, @PathVariable String assignment_id) {
        return assignmentService.getAssessment(assignment_id);
    }

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> createAssignment(@PathVariable String course_id, @Valid @RequestBody AssessmentDto assignment) {
        return assignmentService.createAssessment(course_id, AssessmentType.ASSIGNMENT, assignment);
    }

    @PutMapping("/{assignment_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> updateAssignment(@PathVariable String course_id, @PathVariable String assignment_id,
                                              @Valid @RequestBody AssessmentDto assignment) {
        return assignmentService.updateAssessment(assignment_id, AssessmentType.ASSIGNMENT, assignment);
    }

    @DeleteMapping("/{assignment_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> deleteAssignment(@PathVariable String course_id, @PathVariable String assignment_id) {
        return assignmentService.deleteAssessment(assignment_id);
    }
}
