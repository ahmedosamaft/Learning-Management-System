package fci.swe.advanced_software.controllers.instructor;

import com.fasterxml.jackson.databind.ObjectMapper;
import fci.swe.advanced_software.controllers.users.instructor.InstructorAnnouncementController;
import fci.swe.advanced_software.dtos.course.announcement.AnnouncementRequestDto;
import fci.swe.advanced_software.dtos.course.announcement.AnnouncementResponseDto;
import fci.swe.advanced_software.services.auth.JwtService;
import fci.swe.advanced_software.services.courses.IMediaService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InstructorAnnouncementController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class InstructorAnnouncementsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private IMediaService mediaService;

    @MockBean
    private AnnouncementResponseDto announcementResponseDto;

    @MockBean
    private IAnnouncementService announcementService;

    AnnouncementRequestDto announcementRequestDto;

    @BeforeEach
    void init() {
        announcementRequestDto = AnnouncementRequestDto.builder()
                .title("title")
                .content("content")
                .build();
        announcementResponseDto = AnnouncementResponseDto.builder()
                .id("id")
                .title("title")
                .content("content")
                .build();
    }

    @Test
    void InstructorAnnouncementsController_CreateAnnouncement_ReturnsCreated() throws Exception {

        String courseId = UUID.randomUUID().toString();
        when(announcementService.createAnnouncement(any()))
                .thenReturn(ResponseEntityBuilder
                        .create().withStatus(HttpStatus.CREATED)
                        .withData("announcement", "announcement")
                        .withMessage("Announcement created successfully")
                        .build());

        String response = objectMapper.writeValueAsString(
                Map.of("data", Map.of("announcement", "announcement"),
                        "message", "Announcement created successfully")
        );

        mockMvc.perform(post(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/announcements", courseId)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(announcementRequestDto))
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorAnnouncementsController_GetAnnouncements_ReturnsAnnouncements() throws Exception {
        String courseId = UUID.randomUUID().toString();

        when(announcementService.getAnnouncements(eq(courseId), eq(1), eq(10)))
                .thenReturn(ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .withData("announcements", "announcement")
                        .build());

        String response = objectMapper.writeValueAsString(
                Map.of("data",
                        Map.of("announcements", "announcement")
                )
        );

        mockMvc.perform(get(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/announcements", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorAnnouncementsController_DeleteAnnouncement_ReturnsDeleted() throws Exception {
        String courseId = UUID.randomUUID().toString();
        String announcementId = UUID.randomUUID().toString();

        when(announcementService.deleteAnnouncement(any()))
                .thenReturn(ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.NO_CONTENT)
                        .withMessage("Announcement deleted successfully")
                        .build());

        String response = objectMapper.writeValueAsString(
                Map.of("message", "Announcement deleted successfully")
        );

        mockMvc.perform(delete(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/announcements/{announcementId}", courseId, announcementId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorAnnouncementsController_UpdateAnnouncement_ReturnsUpdated() throws Exception {
        String courseId = UUID.randomUUID().toString();
        String announcementId = UUID.randomUUID().toString();

        when(announcementService.updateAnnouncement(eq(announcementId), any()))
                .thenReturn(ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .withData("announcement", "announcement")
                        .withMessage("Announcement updated successfully")
                        .build());

        String response = objectMapper.writeValueAsString(
                Map.of("data", Map.of("announcement", "announcement"),
                        "message", "Announcement updated successfully")
        );

        mockMvc.perform(put(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/announcements/{announcementId}", courseId, announcementId)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(announcementRequestDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorAnnouncementsController_GetAnnouncement_ReturnsAnnouncement() throws Exception {
        String courseId = UUID.randomUUID().toString();
        String announcementId = UUID.randomUUID().toString();

        when(announcementService.getAnnouncement(eq(announcementId)))
                .thenReturn(ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .withData("announcement", "announcement")
                        .build());

        String response = objectMapper.writeValueAsString(
                Map.of("data", Map.of("announcement", "announcement"))
        );

        mockMvc.perform(get(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/announcements/{announcementId}", courseId, announcementId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }
}
