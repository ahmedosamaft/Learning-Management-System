package fci.swe.advanced_software.services.courses.announcement;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.course.announcement.AnnouncementRequestDto;
import fci.swe.advanced_software.dtos.course.announcement.CommentDto;
import org.springframework.http.ResponseEntity;

public interface IAnnouncementService {
    ResponseEntity<Response> createAnnouncement(AnnouncementRequestDto requestDto);

    ResponseEntity<Response> updateAnnouncement(String id, AnnouncementRequestDto requestDto);

    ResponseEntity<Response> getAnnouncement(String id);

    ResponseEntity<Response> deleteAnnouncement(String id);

    ResponseEntity<Response> getAnnouncements(String course, Integer page, Integer size);

    ResponseEntity<Response> createComment(String announcementId, CommentDto comment);

    ResponseEntity<Response> updateComment(String announcementId, String commentId, CommentDto comment);

    ResponseEntity<Response> deleteComment(String announcementId, String commentId);
}