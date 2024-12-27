package fci.swe.advanced_software.services.courses;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.course.announcement.AnnouncementRequestDto;
import fci.swe.advanced_software.dtos.course.announcement.AnnouncementResponseDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.repositories.course.AnnouncementRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.services.INotificationsService;
import fci.swe.advanced_software.services.courses.announcement.AnnouncementService;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.mappers.courses.AnnouncementMapper;
import fci.swe.advanced_software.utils.mappers.users.UserResponseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AnnouncementServiceTests {

    @InjectMocks
    private AnnouncementService announcementService;

    @Mock
    private AnnouncementRepository announcementRepository;

    @Mock
    private INotificationsService notificationsService;

    @Mock
    private AnnouncementMapper announcementMapper;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AuthUtils authUtils;

    @Mock
    private RepositoryUtils repositoryUtils;

    @Mock
    private UserResponseMapper userResponseMapper;

    @Test
    public void createAnnouncement_ShouldReturnCreated() {
        AbstractUser user = AbstractUser.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        Announcement announcement = Announcement.builder()
                .id(UUID.randomUUID().toString())
                .title("announcement title")
                .content("announcement content")
                .course(new Course())
                .build();

        AnnouncementRequestDto requestDto = AnnouncementRequestDto.builder()
                .courseId(UUID.randomUUID().toString())
                .userId(user.getId())
                .title("announcement title")
                .content("announcement content")
                .build();

        AnnouncementResponseDto responseDto = AnnouncementResponseDto.builder()
                .id(announcement.getId())
                .courseId(requestDto.getCourseId())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        when(announcementMapper.toEntity(requestDto)).thenReturn(announcement);
        when(announcementRepository.save(announcement)).thenReturn(announcement);
        when(announcementMapper.toResponseDto(announcement)).thenReturn(responseDto);

        ResponseEntity<Response> response = announcementService.createAnnouncement(requestDto);

        verify(announcementMapper, times(1)).toEntity(requestDto);
        verify(announcementRepository, times(1)).save(announcement);
        verify(announcementMapper, times(1)).toResponseDto(announcement);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Announcement created successfully!", response.getBody().getMessage());
        assertEquals(Map.of("announcement", responseDto), response.getBody().getData());
        String location = Constants.INSTRUCTOR_CONTROLLER + '/' + requestDto.getCourseId() + '/' + announcement.getId();
        assertEquals(location, response.getHeaders().getLocation().toString());
    }

    @Test
    public void updateAnnouncement_AnnouncementFound_ShouldReturnOk() {

        AbstractUser user = AbstractUser.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        Announcement announcement = Announcement.builder()
                .id(UUID.randomUUID().toString())
                .title("announcement title")
                .content("announcement content")
                .build();

        AnnouncementRequestDto requestDto = AnnouncementRequestDto.builder()
                .courseId(UUID.randomUUID().toString())
                .userId(user.getId())
                .title("updated announcement title")
                .content("updated announcement content")
                .build();

        AnnouncementResponseDto responseDto = AnnouncementResponseDto.builder()
                .id(announcement.getId())
                .courseId(requestDto.getCourseId())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        when(announcementRepository.findById(announcement.getId())).thenReturn(Optional.of(announcement));
        when(courseRepository.findById(requestDto.getCourseId())).thenReturn(Optional.empty());
        when(announcementMapper.userDtoToUser(requestDto.getUserId())).thenReturn(user);
        when(announcementMapper.toResponseDto(announcement)).thenReturn(responseDto);
        when(announcementRepository.save(announcement)).thenReturn(announcement);

        ResponseEntity<Response> response = announcementService.updateAnnouncement(announcement.getId(), requestDto);

        verify(announcementRepository, times(1)).findById(announcement.getId());
        verify(courseRepository, times(1)).findById(requestDto.getCourseId());
        verify(announcementMapper, times(1)).userDtoToUser(requestDto.getUserId());
        verify(announcementMapper, times(1)).toResponseDto(announcement);
        verify(announcementRepository, times(1)).save(announcement);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Announcement updated successfully!", response.getBody().getMessage());
        assertEquals(Map.of("announcement", responseDto), response.getBody().getData());
    }

    @Test
    public void updateAnnouncement_AnnouncementNotFound_ShouldReturnNotFound() {
        String announcementId = UUID.randomUUID().toString();
        AnnouncementRequestDto requestDto = AnnouncementRequestDto.builder()
                .courseId(UUID.randomUUID().toString())
                .userId(UUID.randomUUID().toString())
                .title("updated announcement title")
                .content("updated announcement content")
                .build();

        when(announcementRepository.findById(announcementId)).thenReturn(Optional.empty());

        ResponseEntity<Response> response = announcementService.updateAnnouncement(announcementId, requestDto);

        verify(announcementRepository, times(1)).findById(announcementId);
        verify(courseRepository, never()).findById(requestDto.getCourseId());
        verify(announcementRepository, never()).save(any());
        verify(announcementMapper, never()).userDtoToUser(requestDto.getUserId());
        verify(announcementMapper, never()).toResponseDto(any());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Announcement not found!", response.getBody().getMessage());
    }

    @Test
    public void getAnnouncement_AnnouncementFound_ShouldReturnOk() {
        Announcement announcement = Announcement.builder()
                .id(UUID.randomUUID().toString())
                .title("announcement title")
                .content("announcement content")
                .build();

        AnnouncementResponseDto responseDto = AnnouncementResponseDto.builder()
                .id(announcement.getId())
                .courseId(UUID.randomUUID().toString())
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .build();

        when(announcementRepository.findById(announcement.getId())).thenReturn(Optional.of(announcement));
        when(announcementMapper.toResponseDto(announcement)).thenReturn(responseDto);

        ResponseEntity<Response> response = announcementService.getAnnouncement(announcement.getId());

        verify(announcementRepository, times(1)).findById(announcement.getId());
        verify(announcementMapper, times(1)).toResponseDto(announcement);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Announcement retrieved successfully!", response.getBody().getMessage());
        assertEquals(Map.of("announcement", responseDto), response.getBody().getData());
    }

    @Test
    public void getAnnouncement_AnnouncementNotFound_ShouldReturnNotFound() {
        String announcementId = UUID.randomUUID().toString();

        when(announcementRepository.findById(announcementId)).thenReturn(Optional.empty());

        ResponseEntity<Response> response = announcementService.getAnnouncement(announcementId);

        verify(announcementRepository, times(1)).findById(announcementId);
        verify(announcementMapper, never()).toResponseDto(any());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Announcement not found!", response.getBody().getMessage());
    }

    @Test
    public void deleteAnnouncement_AnnouncementFound_ShouldReturnNoContent() {
        Announcement announcement = Announcement.builder()
                .id(UUID.randomUUID().toString())
                .title("announcement title")
                .content("announcement content")
                .build();

        when(announcementRepository.findById(announcement.getId())).thenReturn(Optional.of(announcement));

        ResponseEntity<Response> response = announcementService.deleteAnnouncement(announcement.getId());

        verify(announcementRepository, times(1)).findById(announcement.getId());
        verify(announcementRepository, times(1)).delete(announcement);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Announcement deleted successfully!", response.getBody().getMessage());
    }

    @Test
    public void deleteAnnouncement_AnnouncementNotFound_ShouldReturnNotFound() {
        String announcementId = UUID.randomUUID().toString();

        when(announcementRepository.findById(announcementId)).thenReturn(Optional.empty());

        ResponseEntity<Response> response = announcementService.deleteAnnouncement(announcementId);

        verify(announcementRepository, times(1)).findById(announcementId);
        verify(announcementRepository, never()).delete(any());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Announcement not found!", response.getBody().getMessage());
    }

    @Test
    public void getAnnouncements_ShouldReturnOk() {
        String courseId = UUID.randomUUID().toString();
        int page = 1;
        int size = 10;

        AbstractUser user = AbstractUser.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        Announcement announcement = Announcement.builder()
                .id(UUID.randomUUID().toString())
                .title("announcement title")
                .content("announcement content")
                .postedBy(user)
                .build();

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");

        Page<Announcement> announcements = new PageImpl<>(List.of(announcement), pageable, 1);

        when(repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt")).thenReturn(pageable);
        when(announcementRepository.findAllByCourseId(courseId, pageable)).thenReturn(announcements);
        when(userResponseMapper.toDto(user)).thenReturn(userResponseDto);

        ResponseEntity<Response> response = announcementService.getAnnouncements(courseId, page, size);

        verify(repositoryUtils, times(1)).getPageable(page, size, Sort.Direction.ASC, "createdAt");
        verify(announcementRepository, times(1)).findAllByCourseId(courseId, pageable);
        verify(userResponseMapper, times(1)).toDto(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Announcements retrieved successfully!", response.getBody().getMessage());
    }
}
