package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.services.courses.IMediaService;
import fci.swe.advanced_software.services.courses.ResourceType;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/assignments")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorAssignmentsController {
    private final IAssessmentService assignmentService;
    private final IMediaService mediaService;

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getAllAssignments(@PathVariable String courseId, @RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        return assignmentService.getAllAssessments(courseId, AssessmentType.ASSIGNMENT, page, size);
    }

    @GetMapping("/{assignmentId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getAssignmentById(@PathVariable String courseId, @PathVariable String assignmentId) {
        return assignmentService.getAssessment(assignmentId);
    }

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> createAssignment(@PathVariable String courseId, @Valid @RequestBody AssessmentDto assignment) {
        return assignmentService.createAssessment(courseId, AssessmentType.ASSIGNMENT, assignment);
    }

    @PutMapping("/{assignmentId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> updateAssignment(@PathVariable String courseId, @PathVariable String assignmentId,
                                              @Valid @RequestBody AssessmentDto assignment) {
        return assignmentService.updateAssessment(assignmentId, AssessmentType.ASSIGNMENT, assignment);
    }

    @DeleteMapping("/{assignmentId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteAssignment(@PathVariable String courseId, @PathVariable String assignmentId) {
        return assignmentService.deleteAssessment(assignmentId);
    }

    @PostMapping("/{assignmentId}/media")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> uploadMedia(@PathVariable("courseId") String courseId,
                                         @PathVariable("assignmentId") String assignmentId,
                                         @RequestParam("file") MultipartFile file) {
        return mediaService.uploadFile(assignmentId, ResourceType.ASSESSMENT, file);
    }

    @DeleteMapping("/{assignmentId}/media/{mediaId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteMedia(@PathVariable("courseId") String courseId,
                                         @PathVariable("mediaId") String mediaId) {
        return mediaService.deleteFile(mediaId);
    }
}
