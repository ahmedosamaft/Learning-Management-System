package fci.swe.advanced_software.controllers.course;

import fci.swe.advanced_software.dtos.course.announcement.AnnouncementRequestDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.announcement.IAnnouncementService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.API_VERSION + "/announcements")
@RequiredArgsConstructor
public class AnnouncementController {

    private final IAnnouncementService announcementService;

    @PostMapping
    @RolesAllowed({Roles.ADMIN, Roles.INSTRUCTOR})
    public ResponseEntity<?> createAnnouncement(@Valid @RequestBody AnnouncementRequestDto announcementRequestDto) {
        return announcementService.createAnnouncement(announcementRequestDto);
    }

    @PutMapping("/{id}")
    @RolesAllowed({Roles.ADMIN, Roles.INSTRUCTOR})
    public ResponseEntity<?> updateAnnouncement(@PathVariable String id, @Valid @RequestBody AnnouncementRequestDto announcementRequestDto) {
        return announcementService.updateAnnouncement(id, announcementRequestDto);
    }

    @DeleteMapping("/{id}")
    @RolesAllowed(Roles.ADMIN)
    public ResponseEntity<?> deleteAnnouncement(@PathVariable String id) {
        return announcementService.deleteAnnouncement(id);
    }
}
