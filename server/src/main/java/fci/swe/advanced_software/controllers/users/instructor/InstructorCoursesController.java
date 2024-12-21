package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.course.ICourseService;
import fci.swe.advanced_software.services.users.instructor.IInstructorService;
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

    @GetMapping
    public ResponseEntity<?> getCourses(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer size) {
        return instructorService.getCourses(page, size);
    }

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody @Valid CourseDto courseDto) {
        return courseService.createCourse(courseDto);
    }

    @GetMapping("/{course_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> getCourse(@PathVariable String course_id) {
        return courseService.getCourseById(course_id);
    }

    @PutMapping("/{course_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> updateCourse(@PathVariable String course_id, @RequestBody @Valid CourseDto courseDto) {
        return courseService.updateCourse(course_id, courseDto);
    }

    @DeleteMapping("/{course_id}")
    @PreAuthorize("@authorizationService.isTeaching(#course_id)")
    public ResponseEntity<?> deleteCourse(@PathVariable String course_id) {
        return courseService.deleteCourse(course_id);
    }
}
