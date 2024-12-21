package fci.swe.advanced_software.services.courses.announcement;

import fci.swe.advanced_software.dtos.course.announcement.AnnouncementRequestDto;
import org.springframework.http.ResponseEntity;

public interface IAnnouncementService {
    ResponseEntity<?> createAnnouncement(AnnouncementRequestDto requestDto);

    ResponseEntity<?> updateAnnouncement(String id, AnnouncementRequestDto requestDto);

    ResponseEntity<?> getAnnouncement(String id);

    ResponseEntity<?> deleteAnnouncement(String id);

    ResponseEntity<?> getAnnouncements(String course, Integer page, Integer size);
}