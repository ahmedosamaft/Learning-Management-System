package fci.swe.advanced_software.services.courses.announcement;

import fci.swe.advanced_software.dtos.course.AnnouncementRequestDto;
import fci.swe.advanced_software.dtos.course.AnnouncementResponseDto;
import fci.swe.advanced_software.models.courses.Course;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAnnouncementService {
    ResponseEntity<?> createAnnouncement(AnnouncementRequestDto requestDto);

    ResponseEntity<?> updateAnnouncement(String id, AnnouncementRequestDto requestDto);

    ResponseEntity<AnnouncementResponseDto> getAnnouncement(String id);

    ResponseEntity<?> deleteAnnouncement(String id);

    ResponseEntity<List<AnnouncementResponseDto>> getAllAnnouncementsForCourse(Course course);
}