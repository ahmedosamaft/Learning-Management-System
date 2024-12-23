package fci.swe.advanced_software.controllers.users.student;

import fci.swe.advanced_software.dtos.course.announcement.CommentDto;
import fci.swe.advanced_software.models.users.Roles;
import fci.swe.advanced_software.services.courses.announcement.IAnnouncementService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.STUDENT_CONTROLLER + "/announcements")
@RequiredArgsConstructor
@RolesAllowed(Roles.STUDENT)
public class StudentAnnouncementController {
    private final IAnnouncementService announcementService;

    @GetMapping
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getAnnouncements(@PathVariable @UUID String courseId,
                                              @RequestParam(required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                              @RequestParam(required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size){
        return announcementService.getAnnouncements(courseId, page, size);
    }

    @GetMapping("/{announcementId}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> getAnnouncement(@PathVariable @UUID String courseId, @PathVariable @UUID String announcementId) {
        return announcementService.getAnnouncement(announcementId);
    }

    @PostMapping("/{announcementId}/comments")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> createComment(@PathVariable @UUID String courseId,
                                           @PathVariable @UUID String announcementId,
                                           @RequestBody @Valid CommentDto comment) {
        return announcementService.createComment(announcementId, comment);
    }

    @PutMapping("/{announcementId}/comments/{commentId}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> updateComment(@PathVariable @UUID String courseId,
                                           @PathVariable @UUID String announcementId,
                                           @PathVariable @UUID String commentId,
                                           @RequestBody @Valid CommentDto comment) {
        return announcementService.updateComment(announcementId, commentId, comment);
    }

    @DeleteMapping("/{announcementId}/comments/{commentId}")
    @PreAuthorize("@authorizationService.isEnrolled(#courseId)")
    public ResponseEntity<?> deleteComment(@PathVariable @UUID String courseId,
                                           @PathVariable @UUID String announcementId,
                                           @PathVariable @UUID String commentId) {
        return announcementService.deleteComment(announcementId, commentId);
    }
}
