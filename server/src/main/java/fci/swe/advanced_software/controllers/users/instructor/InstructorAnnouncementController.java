package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.services.courses.announcement.AnnouncementService;
import fci.swe.advanced_software.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{course_id}/announcements")
@RequiredArgsConstructor
public class InstructorAnnouncementController {
    private final AnnouncementService announcementService;


}
