package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.users.instructor.IInstructorService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
