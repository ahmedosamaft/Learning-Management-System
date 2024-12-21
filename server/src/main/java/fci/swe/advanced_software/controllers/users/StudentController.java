package fci.swe.advanced_software.controllers.users;

import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.users.student.IStudentService;
import fci.swe.advanced_software.utils.Constants;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/students")
@RequiredArgsConstructor
@Tag(name = "Student", description = "Student related endpoints")
public class StudentController {
    private final IStudentService studentService;

    @GetMapping("/{id}")
    @RolesAllowed({Roles.INSTRUCTOR, Roles.ADMIN})
    public ResponseEntity<?> getStudent(@PathVariable String id) {
        return studentService.getStudent(id);
    }

    @GetMapping("/courses")
    public ResponseEntity<?> getCourses() {
        return studentService.getCourses();
    }

    @GetMapping("/courses/search")
    public ResponseEntity<?> searchCourses(@RequestParam String keyword) {
        return studentService.searchCourses(keyword);
    }

}
