package fci.swe.advanced_software.controllers.instructor;

import com.fasterxml.jackson.databind.ObjectMapper;

import fci.swe.advanced_software.AdvancedSoftwareApplication;
import fci.swe.advanced_software.config.SecurityConfig;
import fci.swe.advanced_software.controllers.users.instructor.InstructorCoursesController;
import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.services.auth.JwtService;
import fci.swe.advanced_software.services.courses.course.ICourseService;
import fci.swe.advanced_software.services.users.instructor.IInstructorService;
import fci.swe.advanced_software.services.users.student.IStudentService;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(InstructorCoursesController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class InstructorCoursesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ICourseService courseService;

    @MockBean
    private IInstructorService instructorService;

    @MockBean
    private AuthUtils authUtils;

    @MockBean
    private IStudentService studentService;

    @MockBean
    private JwtService jwtService;

    CourseDto courseDto;

    Course course;
    @BeforeEach
    public void Init() {
        courseDto = CourseDto.builder()
                .name("course")
                .code("code")
                .build();
    }

    @Test
    void InstructorCoursesController_GetCourses_ReturnsCourses() throws Exception {
        when(instructorService
                .getCourses(eq(1), eq(10))
        ).thenReturn(ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("testing", "course")
                .withMessage("courses retrieved successfully!")
                .build()
        );

        String response = objectMapper.writeValueAsString(
                Map.of("data", Map.of("testing", "course"),
                        "message", "courses retrieved successfully!")
        );

        mockMvc.perform(get(Constants.INSTRUCTOR_CONTROLLER)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorCoursesController_CreateCourse_ReturnsCourse() throws Exception {
        when(courseService.createCourse(any())
        ).thenReturn(ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withData("testing", "course")
                .withMessage("course created successfully!")
                .build()
        );

        String response = objectMapper.writeValueAsString(
                Map.of("data", Map.of("testing", "course"),
                        "message", "course created successfully!")
        );

        mockMvc.perform(post(Constants.INSTRUCTOR_CONTROLLER)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(courseDto))
                )
                .andExpect(status().isCreated())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorCoursesController_GetCourse_ReturnsCourse() throws Exception {
        String courseId = UUID.randomUUID().toString();

        when(courseService.getCourseById(eq(courseId))
        ).thenReturn(ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("course", courseDto)
                .withMessage("course retrieved successfully!")
                .build()
        );

        String response = objectMapper.writeValueAsString(
                Map.of("data", Map.of("course", courseDto),
                        "message", "course retrieved successfully!")
        );

        mockMvc.perform(get(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorCoursesController_UpdateCourse_ReturnsCourse() throws Exception {
        String courseId = UUID.randomUUID().toString();

        when(courseService.updateCourse(eq(courseId), any())
        ).thenReturn(ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("course", courseDto)
                .withMessage("course updated successfully!")
                .build()
        );

        String response = objectMapper.writeValueAsString(
                Map.of("data", Map.of("course", courseDto),
                        "message", "course updated successfully!")
        );

        mockMvc.perform(put(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(courseDto))
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorCoursesController_DeleteCourse_ReturnsCourse() throws Exception {
        String courseId = UUID.randomUUID().toString();

        when(courseService.deleteCourse(eq(courseId))
        ).thenReturn(ResponseEntityBuilder.create()
                .withStatus(HttpStatus.NO_CONTENT)
                .withMessage("course deleted successfully!")
                .build()
        );

        String response = objectMapper.writeValueAsString(
                Map.of("message", "course deleted successfully!")
        );

        mockMvc.perform(delete(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNoContent())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorCoursesController_GetStudents_ReturnsStudents() throws Exception {
        String courseId = UUID.randomUUID().toString();

        when(courseService.getStudents(eq(courseId), eq(1), eq(10))
        ).thenReturn(ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withData("students", "student")
                .withMessage("students retrieved successfully!")
                .build()
        );

        String response = objectMapper.writeValueAsString(
                Map.of("data", Map.of("students", "student"),
                        "message", "students retrieved successfully!")
        );

        mockMvc.perform(get(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/students", courseId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorCoursesController_AddStudentToCourse_ReturnsStudent() throws Exception {
        String courseId = UUID.randomUUID().toString();
        String studentId = UUID.randomUUID().toString();

        when(studentService.enrollCourse(eq(courseId), eq(studentId))
        ).thenReturn(ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("student added successfully!")
                .build()
        );

        String response = objectMapper.writeValueAsString(
                Map.of("message", "student added successfully!")
        );

        mockMvc.perform(put(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/students/{studentId}", courseId, studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }

    @Test
    void InstructorCoursesController_RemoveStudentFromCourse_ReturnsStudent() throws Exception {
        String courseId = UUID.randomUUID().toString();
        String studentId = UUID.randomUUID().toString();

        when(studentService.dropCourse(eq(courseId), eq(studentId))
        ).thenReturn(ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("student removed successfully!")
                .build()
        );

        String response = objectMapper.writeValueAsString(
                Map.of("message", "student removed successfully!")
        );

        mockMvc.perform(delete(Constants.INSTRUCTOR_CONTROLLER + "/{courseId}/students/{studentId}", courseId, studentId)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(content().json(response));
    }
}
