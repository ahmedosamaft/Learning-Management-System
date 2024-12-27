package fci.swe.advanced_software.controllers.users.instructor;

import fci.swe.advanced_software.dtos.course.announcement.AnnouncementRequestDto;
import fci.swe.advanced_software.dtos.course.announcement.CommentDto;
import fci.swe.advanced_software.services.courses.announcement.IAnnouncementService;
import fci.swe.advanced_software.utils.Constants;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Range;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/announcements")
@RequiredArgsConstructor
public class InstructorAnnouncementController {
    private final IAnnouncementService announcementService;

    @GetMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getAnnouncements(@PathVariable("courseId") String courseId,
                                              @RequestParam(value = "page", required = false, defaultValue = "1") @Min(value = 1) Integer page,
                                              @RequestParam(value = "size", required = false, defaultValue = "10") @Range(min = 1, max = 100) Integer size) {
        return announcementService.getAnnouncements(courseId, page, size);
    }

    @PostMapping
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> createAnnouncement(@PathVariable("courseId") String courseId,
                                                @RequestBody AnnouncementRequestDto requestDto) {
        requestDto.setCourseId(courseId);
        return announcementService.createAnnouncement(requestDto);
    }

    @GetMapping("/{announcementId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> getAnnouncement(@PathVariable("courseId") String courseId,
                                            @PathVariable("announcementId") String announcementId) {
        return announcementService.getAnnouncement(announcementId);
    }

    @PutMapping("/{announcementId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> updateAnnouncement(@PathVariable("courseId") String courseId,
                                                @PathVariable("announcementId") String announcementId,
                                                @RequestBody AnnouncementRequestDto requestDto) {
        return announcementService.updateAnnouncement(announcementId, requestDto);
    }

    @DeleteMapping("/{announcementId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteAnnouncement(@PathVariable("courseId") String courseId,
                                                @PathVariable("announcementId") String announcementId) {
        return announcementService.deleteAnnouncement(announcementId);
    }

    @PostMapping("/{announcementId}/comments")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> createComment(@PathVariable("courseId") String courseId,
                                           @PathVariable("announcementId") String announcementId,
                                           @Valid @RequestBody CommentDto comment) {
        return announcementService.createComment(announcementId, comment);
    }

    @PutMapping("/{announcementId}/comments/{commentId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> updateComment(@PathVariable("courseId") String courseId,
                                           @PathVariable("announcementId") String announcementId,
                                           @PathVariable("commentId") String commentId,
                                           @RequestBody CommentDto comment) {
        return announcementService.updateComment(announcementId, commentId, comment);
    }

    @DeleteMapping("/{announcementId}/comments/{commentId}")
    @PreAuthorize("@authorizationService.isTeaching(#courseId)")
    public ResponseEntity<?> deleteComment(@PathVariable("courseId") String courseId,
                                           @PathVariable("announcementId") String announcementId,
                                           @PathVariable("commentId") String commentId) {
        return announcementService.deleteComment(announcementId, commentId);
    }
}
