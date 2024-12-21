package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.course.AnnouncementRequestDto;
import fci.swe.advanced_software.services.courses.announcement.AnnouncementService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}/announcements")
@RequiredArgsConstructor
public class InstructorAnnouncementController {
    private final AnnouncementService announcementService;


}
