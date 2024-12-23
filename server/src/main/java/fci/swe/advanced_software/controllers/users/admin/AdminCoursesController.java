package fci.swe.advanced_software.controllers.users.admin;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.course.ICourseService;
import fci.swe.advanced_software.services.users.student.StudentService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/admin/courses")
@AllArgsConstructor
@RolesAllowed(Roles.ADMIN)
public class AdminCoursesController {
    private final ICourseService courseService;
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<?> createCourse(@Valid @RequestBody CourseDto courseDto) {
        return courseService.createCourse(courseDto);
    }

    @GetMapping
    public ResponseEntity<?> getCourses(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        return courseService.getAllCourses(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable String id) {
        return courseService.deleteCourse(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCourse(@PathVariable String id, @Valid @RequestBody CourseDto courseDto) {
        return courseService.updateCourse(id, courseDto);
    }

    @GetMapping("/{courseId}/students")
    public ResponseEntity<?> getStudents(@PathVariable String courseId,
                                         @RequestParam(required = false, defaultValue = "1") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer size) {
        return courseService.getStudents(courseId, page, size);
    }

    @PutMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<?> addStudentToCourse(@PathVariable String courseId, @PathVariable String studentId) {
        return studentService.enrollCourse(courseId, studentId);
    }

    @DeleteMapping("/{courseId}/students/{studentId}")
    public ResponseEntity<?> removeStudentFromCourse(@PathVariable String courseId, @PathVariable String studentId) {
        return studentService.dropCourse(courseId, studentId);
    }
}