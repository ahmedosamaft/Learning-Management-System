package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}")
@RequiredArgsConstructor
@RolesAllowed(Roles.INSTRUCTOR)
public class InstructorQuizFeedback {


}
