package fci.swe.advanced_software.controllers.course;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.course.ICourseService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/courses")
@AllArgsConstructor
public class CourseController {

    private final ICourseService courseService;

    @GetMapping
    public ResponseEntity<?> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable String id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    @RolesAllowed({Roles.INSTRUCTOR, Roles.ADMIN})
    public ResponseEntity<?> createCourse(@RequestBody CourseDto courseDto) {
        return courseService.createCourse(courseDto);
    }

    @PutMapping("/{id}")
    @RolesAllowed({Roles.INSTRUCTOR, Roles.ADMIN})
    public ResponseEntity<?> updateCourse(@PathVariable String id, @RequestBody CourseDto courseDto) {
        return courseService.updateCourse(id, courseDto);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed({Roles.INSTRUCTOR, Roles.ADMIN})
    public ResponseEntity<?> deleteCourse(@PathVariable String code) {
        return courseService.deleteCourse(code);
    }
}
