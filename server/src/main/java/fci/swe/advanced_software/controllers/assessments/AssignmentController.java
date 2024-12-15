package fci.swe.advanced_software.controllers.assessments;

import fci.swe.advanced_software.dtos.assessments.AssignmentRequestDto;
import fci.swe.advanced_software.models.assessments.Assignment;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.assessments.IAssignmentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(Constants.API_VERSION + "/assignments")
@RequiredArgsConstructor
@RolesAllowed({Roles.ADMIN, Roles.INSTRUCTOR})
public class AssignmentController {
    private final IAssignmentService assignmentService;

    @PostMapping
    public ResponseEntity<?> createAssignment(@Valid @RequestBody AssignmentRequestDto assignment) {
        return assignmentService.createAssignment(assignment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAssignment(@PathVariable String id) {
        return assignmentService.getAssignment(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAssignment(@PathVariable String id, @Valid @RequestBody AssignmentRequestDto assignment) {
        return assignmentService.updateAssignment(id, assignment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAssignment(@PathVariable String id) {
        return assignmentService.deleteAssignment(id);
    }
    @GetMapping("/course/{courseId}")
    public ResponseEntity<?> getAssignmentsByCourse(@PathVariable String courseId) {
        Course course = new Course();
        course.setId(courseId);
        return assignmentService.getAssignmentsByCourse(course);
    }
}
