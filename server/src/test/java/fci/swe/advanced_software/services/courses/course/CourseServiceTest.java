package fci.swe.advanced_software.services.courses.course;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.course.CourseRepository;
// import fci.swe.advanced_software.repositories.course.CourseSearchRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.mappers.courses.CourseMapper;
import fci.swe.advanced_software.utils.mappers.courses.CourseToElasticsearchMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CourseServiceTest {

    @InjectMocks
    private CourseService courseService;

    @Mock
    private CourseRepository courseRepository;

    // @Mock
    // private CourseSearchRepository courseSearchRepository;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private CourseToElasticsearchMapper courseToElasticsearchMapper;

    @Mock
    private RepositoryUtils repositoryUtils;

    private Course buildMockCourse() {
        return Course.builder()
                .id("CS101")
                .code("CS101")
                .name("Introduction to Computer Science")
                .instructor(buildMockInstructor())
                .build();
    }

    private Instructor buildMockInstructor() {
        return Instructor.builder()
                .id("INS001")
                .name("John Doe")
                .build();
    }

    private CourseDto buildMockCourseDto() {
        return CourseDto.builder()
                .id("CS101")
                .code("CS101")
                .name("Introduction to Computer Science")
                .build();
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void CourseService_GetAllCourses_ReturnsCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(buildMockCourse());
        Page<Course> coursePage = new PageImpl<>(courseList);

        when(repositoryUtils.getPageable(anyInt(), anyInt(), any(), any())).thenReturn(PageRequest.of(0, 10));
        when(courseRepository.findAll(any(PageRequest.class))).thenReturn(coursePage);
        when(courseMapper.toDto(any(Course.class))).thenReturn(buildMockCourseDto());

        ResponseEntity<?> response = courseService.getAllCourses(0, 10);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void CourseService_GetCourseById_ReturnsCourse() {
        when(courseRepository.findById("CS101")).thenReturn(Optional.of(buildMockCourse()));
        when(courseMapper.toDto(any(Course.class))).thenReturn(buildMockCourseDto());

        ResponseEntity<?> response = courseService.getCourseById("CS101");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
    @Test
    void CourseService_GetCourseById_ReturnsNotFound() {
        when(courseRepository.findById("CS999")).thenReturn(Optional.empty());

        ResponseEntity<?> response = courseService.getCourseById("CS999");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void CourseService_CreateCourse_ReturnsCreatedCourse() {
        Course course = buildMockCourse();
        CourseDto courseDto = buildMockCourseDto();

        when(courseMapper.toEntity(courseDto)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseToElasticsearchMapper.toES(course)).thenReturn(null);
        // when(courseSearchRepository.save(any())).thenReturn(null);
        when(courseMapper.toDto(course)).thenReturn(courseDto); // Ensure DTO mapping

        ResponseEntity<?> response = courseService.createCourse(courseDto);
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void CourseService_UpdateCourse_ReturnsUpdatedCourse() {
        Course course = buildMockCourse();
        CourseDto courseDto = buildMockCourseDto();

        when(courseRepository.findById("CS101")).thenReturn(Optional.of(course));
        when(courseMapper.toEntity(courseDto)).thenReturn(course);
        when(courseRepository.save(course)).thenReturn(course);
        when(courseToElasticsearchMapper.toES(course)).thenReturn(null);
        // when(courseSearchRepository.save(any())).thenReturn(null);

        ResponseEntity<?> response = courseService.updateCourse("CS101", courseDto);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }
    @Test
    void CourseService_UpdateCourse_ReturnsNotFound() {
        when(courseRepository.findById("CS999")).thenReturn(Optional.empty());

        ResponseEntity<?> response = courseService.updateCourse("CS999", buildMockCourseDto());
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }


    @Test
    void CourseService_DeleteCourse_DeletesCourse() {
        when(courseRepository.existsById("CS101")).thenReturn(true);
        doNothing().when(courseRepository).deleteById("CS101");

        ResponseEntity<?> response = courseService.deleteCourse("CS101");
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }
    @Test
    void CourseService_DeleteCourse_ReturnsNotFound() {
        when(courseRepository.existsById("CS999")).thenReturn(false);

        ResponseEntity<?> response = courseService.deleteCourse("CS999");
        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
    }
}