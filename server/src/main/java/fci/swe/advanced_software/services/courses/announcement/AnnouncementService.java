package fci.swe.advanced_software.services.courses.announcement;

import fci.swe.advanced_software.dtos.course.AnnouncementRequestDto;
import fci.swe.advanced_software.dtos.course.AnnouncementResponseDto;
import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.course.AnnouncementRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.courses.AnnouncementMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnnouncementService implements IAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final CourseRepository courseRepository;

    @Override
    public ResponseEntity<?> createAnnouncement(AnnouncementRequestDto requestDto) {
        Announcement announcement = announcementMapper.toEntity(requestDto);
        announcement = announcementRepository.save(announcement);

        AnnouncementResponseDto responseDto = announcementMapper.toResponseDto(announcement);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation(Constants.API_VERSION + "/announcements/" + announcement.getId())
                .withData("announcement", responseDto)
                .withMessage("Announcement created successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> updateAnnouncement(String id, AnnouncementRequestDto requestDto) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);

        if (announcement == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Announcement not found!")
                    .build();
        }

        if (requestDto.getCourseId() != null) {
            announcement.setCourse(courseRepository.findById(requestDto.getCourseId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid course ID: " + requestDto.getCourseId())));
        }

        if (requestDto.getTitle() != null) {
            announcement.setTitle(requestDto.getTitle());
        }

        if (requestDto.getContent() != null) {
            announcement.setContent(requestDto.getContent());
        }

        if (requestDto.getPostedByUserId() != null) {
            announcement.setPostedBy(announcementMapper.userDtoToUser(requestDto.getPostedByUserId()));
        }

        announcement = announcementRepository.save(announcement);

        AnnouncementResponseDto responseDto = announcementMapper.toResponseDto(announcement);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("announcement", responseDto)
                .withMessage("Announcement updated successfully!")
                .build();
    }

    @Override
    public ResponseEntity<AnnouncementResponseDto> getAnnouncement(String id) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);

        if (announcement == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        AnnouncementResponseDto responseDto = announcementMapper.toResponseDto(announcement);

        return ResponseEntity.ok(responseDto);
    }

    @Override
    public ResponseEntity<?> deleteAnnouncement(String id) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);

        if (announcement == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Announcement not found!")
                    .build();
        }

        announcementRepository.delete(announcement);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("Announcement deleted successfully!")
                .build();
    }

    @Override
    public ResponseEntity<List<AnnouncementResponseDto>> getAllAnnouncementsForCourse(Course course) {
        List<Announcement> announcements = announcementRepository.findByCourse(course);

        if (announcements.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<AnnouncementResponseDto> responseDtos = announcements.stream()
                .map(announcementMapper::toResponseDto)
                .toList();

        return ResponseEntity.ok(responseDtos);
    }
}