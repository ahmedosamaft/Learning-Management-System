package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.users.instructor.IInstructorService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/instructors")
@RequiredArgsConstructor
public class InstructorController {
    private final IInstructorService instructorService;

    @GetMapping("/{id}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<?> getInstructor(@PathVariable String id) {
        return instructorService.getInstructor(id);
    }

    @GetMapping("")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<?> getAllInstructors(@RequestParam(required = false, defaultValue = "1") Integer page,
                                               @RequestParam(required = false, defaultValue = "10") Integer size) {
        return instructorService.getAllInstructors(page, size);
    }
}
