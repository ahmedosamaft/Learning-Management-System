package fci.swe.advanced_software.controllers.users.student;

import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.users.student.IStudentService;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/students")
@RequiredArgsConstructor
@Tag(name = "Student", description = "Student related endpoints")
@RolesAllowed(Roles.STUDENT)
public class StudentController {
    private final IStudentService studentService;
    private final AuthUtils authUtils;

    @PostMapping("/courses/{courseId}")
    public ResponseEntity<?> enroll(@PathVariable String courseId) {
        return studentService.enrollCourse(courseId, authUtils.getCurrentUserId());
    }

    @DeleteMapping("/courses/{courseId}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> drop(@PathVariable String courseId) {
        return studentService.dropCourse(courseId, authUtils.getCurrentUserId());
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getCourses(@RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                        @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return studentService.getCourses(page, size);
    }

    @GetMapping("/courses/search")
    public ResponseEntity<?> searchCourses(@RequestParam String query,
                                           @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                           @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return studentService.searchCourses(query, page, size);
    }

}
