package fci.swe.advanced_software.services.courses.announcement;

import fci.swe.advanced_software.dtos.course.announcement.AnnouncementListDto;
import fci.swe.advanced_software.dtos.course.announcement.AnnouncementRequestDto;
import fci.swe.advanced_software.dtos.course.announcement.AnnouncementResponseDto;
import fci.swe.advanced_software.dtos.course.announcement.CommentDto;
import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.Comment;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.course.AnnouncementRepository;
import fci.swe.advanced_software.repositories.course.CommentRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.services.INotificationsService;
import fci.swe.advanced_software.repositories.users.AbstractUserRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.courses.AnnouncementMapper;
import fci.swe.advanced_software.utils.mappers.courses.CommentMapper;
import fci.swe.advanced_software.utils.mappers.users.UserResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
@AllArgsConstructor
public class AnnouncementService implements IAnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final AnnouncementMapper announcementMapper;
    private final CourseRepository courseRepository;
    private final RepositoryUtils repositoryUtils;
    private final UserResponseMapper userResponseMapper;
    private final AuthUtils authUtils;
    private final AbstractUserRepository<AbstractUser> abstractUserRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final INotificationsService notificationsService;

    @Override
    public ResponseEntity<?> createAnnouncement(AnnouncementRequestDto requestDto) {
        requestDto.setUserId(authUtils.getCurrentUserId());
        Announcement announcement = announcementMapper.toEntity(requestDto);
        announcement.setPostedAt(Timestamp.from(Instant.now()));
        announcement = announcementRepository.save(announcement);

        AnnouncementResponseDto responseDto = announcementMapper.toResponseDto(announcement);

        notificationsService.broadcastNotification(
                "New Announcement",
                "A new announcement has been posted in " + announcement.getCourse().getName(),
                announcement.getCourse().getId(),
                Role.STUDENT
        );

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withLocation(Constants.INSTRUCTOR_CONTROLLER + '/' + requestDto.getCourseId() + '/' + announcement.getId())
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

        Course course = courseRepository.findById(requestDto.getCourseId()).orElse(null);
        announcement.setCourse(course);
        announcement.setTitle(requestDto.getTitle());
        announcement.setContent(requestDto.getContent());
        announcement.setPostedBy(announcementMapper.userDtoToUser(requestDto.getUserId()));
        announcement = announcementRepository.save(announcement);

        AnnouncementResponseDto responseDto = announcementMapper.toResponseDto(announcement);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("announcement", responseDto)
                .withMessage("Announcement updated successfully!")
                .build();
    }

    @Override
    public ResponseEntity<?> getAnnouncement(String id) {
        Announcement announcement = announcementRepository.findById(id).orElse(null);

        if (announcement == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Announcement not found!")
                    .build();
        }

        AnnouncementResponseDto responseDto = announcementMapper.toResponseDto(announcement);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("announcement", responseDto)
                .withMessage("Announcement retrieved successfully!")
                .build();
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
    public ResponseEntity<?> getAnnouncements(String courseId, Integer page, Integer size) {
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Announcement> announcements = announcementRepository.findByCourseId(courseId, pageable);

        List<AnnouncementListDto> response = announcements.map(
                        (announcement) ->
                                AnnouncementListDto.builder()
                                        .id(announcement.getId())
                                        .title(announcement.getTitle())
                                        .author(userResponseMapper.toDto(announcement.getPostedBy()))
                                        .postedAt(announcement.getPostedAt())
                                        .build()
                )
                .toList();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("announcements", response)
                .build();
    }

    @Override
    public ResponseEntity<?> createComment(String announcementId, CommentDto content) {
        Announcement announcement = announcementRepository.findById(announcementId).orElse(null);

        if (announcement == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Announcement not found!")
                    .build();
        }
        AbstractUser author = abstractUserRepository.findById(authUtils.getCurrentUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
        Comment comment = Comment.builder()
                .content(content.getContent())
                .announcement(announcement)
                .author(author)
                .build();

        comment = commentRepository.save(comment);

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Comment created successfully!")
                .withData("comment", commentMapper.toResponseDto(comment))
                .build();
    }

    @Override
    public ResponseEntity<?> updateComment(String announcementId, String commentId, CommentDto content) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Comment not found!")
                    .build();
        }

        if (!announcementRepository.existsById(announcementId)) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Announcement not found!")
                    .build();
        }

        if (!comment.getAnnouncement().getId().equals(announcementId)) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .withMessage("Comment not found in the announcement!")
                    .build();
        }

        if (!comment.getAuthor().getId().equals(authUtils.getCurrentUserId())) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.FORBIDDEN)
                    .withMessage("You are not allowed to update this comment!")
                    .build();
        }

        comment.setContent(content.getContent());

        comment = commentRepository.save(comment);
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Comment updated successfully!")
                .withData("comment", commentMapper.toResponseDto(comment))
                .build();
    }

    @Override
    public ResponseEntity<?> deleteComment(String announcementId, String commentId) {
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (comment == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Comment not found!")
                    .build();
        }

        if (!announcementRepository.existsById(announcementId)) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Announcement not found!")
                    .build();
        }

        if (!comment.getAnnouncement().getId().equals(announcementId)) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.BAD_REQUEST)
                    .withMessage("Comment not found in the announcement!")
                    .build();
        }

        if (!comment.getAuthor().getId().equals(authUtils.getCurrentUserId())) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.FORBIDDEN)
                    .withMessage("You are not allowed to update this comment!")
                    .build();
        }

        commentRepository.delete(comment);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}