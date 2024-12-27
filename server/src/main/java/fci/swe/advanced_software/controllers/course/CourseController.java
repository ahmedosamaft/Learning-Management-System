package fci.swe.advanced_software.controllers.course;

import fci.swe.advanced_software.services.courses.course.ICourseService;
import fci.swe.advanced_software.services.users.student.IStudentService;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
public class CourseController {

    private final ICourseService courseService;
    private final IStudentService studentService;

    @GetMapping
    public ResponseEntity<?> getAllCourses(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        return courseService.getAllCourses(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id);
    }

    @GetMapping("/courses/search")
    public ResponseEntity<?> searchCourses(@RequestParam String query,
                                           @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                           @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return studentService.searchCourses(query, page, size);
    }
}
