package fci.swe.advanced_software.controllers.student;

import com.fasterxml.jackson.databind.ObjectMapper;

import fci.swe.advanced_software.AdvancedSoftwareApplication;
import fci.swe.advanced_software.config.SecurityConfig;
import fci.swe.advanced_software.controllers.users.student.StudentAnnouncementController;
import fci.swe.advanced_software.dtos.course.announcement.AnnouncementListDto;
import fci.swe.advanced_software.dtos.course.announcement.AnnouncementResponseDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.services.auth.JwtService;
import fci.swe.advanced_software.services.courses.announcement.IAnnouncementService;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentAnnouncementController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {SecurityConfig.class, AdvancedSoftwareApplication.class})
public class StudentAnnouncementControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAnnouncementService announcementService;

    @MockBean
    private JwtService jwtService;

    @Autowired
    private ObjectMapper objectMapper;

    AnnouncementListDto announcementListDto;

    AbstractUser user;

    Announcement announcement;

    AnnouncementResponseDto announcementResponseDto;

    List<AnnouncementListDto> announcementListDtos;

    UserResponseDto userResponseDto;

    @BeforeEach
    public void Init() {
        user = AbstractUser.builder()
                .id(UUID.randomUUID().toString())
                .email("test@test.com")
                .role(Role.ADMIN)
                .password("password")
                .createdAt(Instant.now())
                .updatedAt(Instant.now())
                .build();

        userResponseDto = UserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole())
                .build();

        announcement = Announcement.builder()
                .title("title")
                .postedBy(user)
                .content("content")
                .postedAt(Timestamp.from(Instant.now()))
                .build();

        announcementResponseDto = AnnouncementResponseDto.builder()
                .id(announcement.getId())
                .author(userResponseDto)
                .title(announcement.getTitle())
                .content(announcement.getContent())
                .build();

        announcementListDto = AnnouncementListDto.builder()
                .id(announcement.getId())
                .author(userResponseDto)
                .title(announcement.getTitle())
                .postedAt(Timestamp.from(Instant.now()))
                .build();

        announcementListDtos = List.of(announcementListDto);
    }

    @Test
    void StudentAnnouncementController_GetAnnouncementWithCourseId_ReturnAnnouncements() throws Exception {
        String courseId = UUID.randomUUID().toString();

        when(announcementService.getAnnouncements(eq(courseId), eq(1), eq(10)))
                .thenReturn(ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .withData("announcements", announcementListDtos)
                        .build());

        String response = objectMapper.writeValueAsString(
                Map.of("data",
                        Map.of("announcements", announcementListDtos)
                )
        );

        mockMvc.perform(get(Constants.STUDENT_CONTROLLER + "/announcements", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }


    @Test
    void StudentAnnouncementController_GetAnnouncementWithCourseIdNotUUID_ReturnValidationError() throws Exception {
        when(announcementService.getAnnouncements(String.valueOf(any(UUID.class)), eq(1), eq(10)))
                .thenReturn(ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .build());


        String response = objectMapper.writeValueAsString(Map.of(
                "error", "Validation failure",
                "message", "Validation failure"
        ));

        mockMvc.perform(get(Constants.STUDENT_CONTROLLER + "/announcements", "WRONG_UUID")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(response));
    }

    @Test
    void StudentAnnouncementController_GetAnnouncementWithCourseIdNegativePageNumber_ReturnValidationError() throws Exception {
        when(announcementService.getAnnouncements(String.valueOf(any(UUID.class)), anyInt(), eq(10)))
                .thenReturn(ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .build());


        String response = objectMapper.writeValueAsString(Map.of(
                "error", "Validation failure",
                "message", "Validation failure"
        ));

        mockMvc.perform(get(Constants.STUDENT_CONTROLLER + "/announcements", UUID.randomUUID())
                        .param("page", "-1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(response));
    }

    @Test
    void StudentAnnouncementController_GetAnnouncementWithCourseIdNegativeSize_ReturnValidationError() throws Exception {
        when(announcementService.getAnnouncements(String.valueOf(any(UUID.class)), eq(1), anyInt()))
                .thenReturn(ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .build());


        String response = objectMapper.writeValueAsString(Map.of(
                "error", "Validation failure",
                "message", "Validation failure"
        ));

        mockMvc.perform(get(Constants.STUDENT_CONTROLLER + "/announcements", UUID.randomUUID())
                        .param("size", "-1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().json(response));
    }

    @Test
    void StudentAnnouncementController_GetAnnouncementWithValidUUIDs_ReturnsAnnouncement() throws Exception {
        String courseId = UUID.randomUUID().toString();
        String announcementId = UUID.randomUUID().toString();

        when(announcementService.getAnnouncement(eq(announcementId)))
                .thenReturn(ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .withData("announcement", announcementResponseDto)
                        .build());

        String response = objectMapper.writeValueAsString(
                Map.of("data", Map.of("announcement", announcementResponseDto))
        );

        mockMvc.perform(get(Constants.STUDENT_CONTROLLER + "/announcements/{announcementId}", courseId, announcementId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void StudentAnnouncementController_GetAnnouncementWithInvalidCourseId_ReturnsValidationError() throws Exception {
        String announcementId = UUID.randomUUID().toString();

        String response = objectMapper.writeValueAsString(Map.of(
                "error", "Validation failure",
                "message", "Validation failure"
        ));

        mockMvc.perform(get(Constants.STUDENT_CONTROLLER + "/announcements/{announcementId}", "WRONG_COURSE_ID", announcementId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(response));
    }

    @Test
    void StudentAnnouncementController_GetAnnouncementWithInvalidAnnouncementId_ReturnsValidationError() throws Exception {
        String courseId = UUID.randomUUID().toString();

        String response = objectMapper.writeValueAsString(Map.of(
                "error", "Validation failure",
                "message", "Validation failure"
        ));

        mockMvc.perform(get(Constants.STUDENT_CONTROLLER + "/announcements/{announcementId}", courseId, "WRONG_ANNOUNCEMENT_ID")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().json(response));
    }
}