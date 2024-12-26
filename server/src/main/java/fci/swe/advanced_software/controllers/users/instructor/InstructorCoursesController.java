package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.course.ICourseService;
import fci.swe.advanced_software.services.users.instructor.IInstructorService;
import fci.swe.advanced_software.services.users.student.StudentService;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER)
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorCoursesController {
    private final ICourseService courseService;
    private final IInstructorService instructorService;
    private final AuthUtils authUtils;
    private final StudentService studentService;

    @GetMapping
    public ResponseEntity<?> getCourses(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        return instructorService.getCourses(page, size);
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody @Valid CourseDto courseDto) {
        courseDto.setInstructorId(authUtils.getCurrentUserId());
        return courseService.createCourse(courseDto);
    }

    @GetMapping("/{courseId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getCourse(@PathVariable String courseId) {
        return courseService.getCourseById(courseId);
    }

    @PutMapping("/{courseId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> updateCourse(@PathVariable String courseId, @RequestBody @Valid CourseDto courseDto) {
        return courseService.updateCourse(courseId, courseDto);
    }

    @DeleteMapping("/{courseId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteCourse(@PathVariable String courseId) {
        return courseService.deleteCourse(courseId);
    }

    @GetMapping("/{courseId}/students")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getStudents(@PathVariable String courseId,
                                         @RequestParam(required = false, defaultValue = "1") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        return courseService.getStudents(courseId, page, size);
    }

    @PutMapping("/{courseId}/students/{studentId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> addStudentToCourse(@PathVariable String courseId, @PathVariable String studentId) {
        return studentService.enrollCourse(courseId, studentId);
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> removeStudentFromCourse(@PathVariable String courseId, @PathVariable String studentId) {
        return studentService.dropCourse(courseId, studentId);
    }
}
