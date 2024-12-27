package fci.swe.advanced_software.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import fci.swe.advanced_software.controllers.users.student.StudentAssignmentController;
import fci.swe.advanced_software.dtos.assessments.assessment.AssessmentDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.services.assessments.IAnswerService;
import fci.swe.advanced_software.services.assessments.IAttemptService;
import fci.swe.advanced_software.services.assessments.assessment.IAssessmentService;
import fci.swe.advanced_software.services.auth.JwtService;
import fci.swe.advanced_software.utils.AuthUtils;
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

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentAssignmentController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class StudentAssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private IAssessmentService assessmentService;

    @MockBean
    private IAttemptService attemptService;

    @MockBean
    private IAnswerService answerService;

    @MockBean
    private AuthUtils authUtils;

    @Autowired
    private ObjectMapper objectMapper;

    AbstractUser user;

    UserResponseDto userResponseDto;

    Assessment assigment;

    Course course;

    AssessmentDto assessmentDto;

    List<AssessmentDto> assessments;

    String typeString;

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

        course = Course.builder()
                .id(UUID.randomUUID().toString())
                .code("TEST")
                .name("test")
                .description("test")
                .build();


        assigment = Assessment.builder()
                .id(UUID.randomUUID().toString())
                .course(course)
                .maxScore(100)
                .updatedAt(Instant.now())
                .createdAt(Instant.now())
                .endsAt(Timestamp.from(Instant.now()))
                .startsAt(Timestamp.from(Instant.now()))
                .instructions("Instructions")
                .build();

        assessmentDto = AssessmentDto.builder()
                .id(assigment.getId())
                .courseId(assigment.getCourse().getId())
                .instructions(assigment.getInstructions())
                .maxScore(assigment.getMaxScore())
                .startsAt(assigment.getStartsAt())
                .endsAt(assigment.getEndsAt())
                .build();


        assessments = List.of(assessmentDto);

        typeString = "Assignments";
    }

    @Test
    void StudentAssignmentController_GetAssignments_ReturnsAssignments() throws Exception {
        String courseId = course.getId();

        when(assessmentService
                .getAllAssessments(eq(courseId), eq(AssessmentType.ASSIGNMENT), eq(1), eq(10))
        ).thenReturn(ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData(typeString, assessments)
                .withMessage(typeString + " retrieved successfully!")
                .build()
        );

        String response = objectMapper.writeValueAsString(
                Map.of("data", Map.of(typeString, assessments),
                        "message", typeString + " retrieved successfully!")
        );


        mockMvc.perform(
                        get(Constants.STUDENT_CONTROLLER + "/assignments", courseId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(response));


    }


}
