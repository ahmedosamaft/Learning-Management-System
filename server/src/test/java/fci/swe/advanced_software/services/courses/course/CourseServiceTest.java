package fci.swe.advanced_software.services.courses.course;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.CourseSearchRepository;
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

    @Mock
    private CourseSearchRepository courseSearchRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private CourseToElasticsearchMapper courseToElasticsearchMapper;

    @Mock
    private RepositoryUtils repositoryUtils;

    private Course mockCourse;
    private CourseDto mockCourseDto;
    private Instructor mockInstructor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        mockInstructor = new Instructor();
        mockInstructor.setId("INS001");
        mockInstructor.setName("John Doe");

        mockCourse = new Course();
        mockCourse.setId("CS101");
        mockCourse.setCode("CS101");
        mockCourse.setName("Introduction to Computer Science");
        mockCourse.setInstructor(mockInstructor);

        mockCourseDto = new CourseDto();
        mockCourseDto.setId("CS101");
        mockCourseDto.setCode("CS101");
        mockCourseDto.setName("Introduction to Computer Science");
    }

    @Test
    void testGetAllCourses() {
        List<Course> courseList = new ArrayList<>();
        courseList.add(mockCourse);
        Page<Course> coursePage = new PageImpl<>(courseList);

        when(repositoryUtils.getPageable(anyInt(), anyInt(), any(), any())).thenReturn(PageRequest.of(0, 10));
        when(courseRepository.findAll(any(PageRequest.class))).thenReturn(coursePage);
        when(courseMapper.toDto(any(Course.class))).thenReturn(mockCourseDto);

        ResponseEntity<?> response = courseService.getAllCourses(0, 10);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testGetCourseById() {
        when(courseRepository.findById("CS101")).thenReturn(Optional.of(mockCourse));
        when(courseMapper.toDto(mockCourse)).thenReturn(mockCourseDto);

        ResponseEntity<?> response = courseService.getCourseById("CS101");
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testCreateCourse() {
        when(courseMapper.toEntity(any(CourseDto.class))).thenReturn(mockCourse);
        when(courseRepository.save(any(Course.class))).thenReturn(mockCourse);
        when(courseToElasticsearchMapper.toES(mockCourse)).thenReturn(null);
        when(courseSearchRepository.save(any())).thenReturn(null);
        when(courseMapper.toDto(mockCourse)).thenReturn(mockCourseDto);

        ResponseEntity<?> response = courseService.createCourse(mockCourseDto);
        assertNotNull(response);
        assertEquals(201, response.getStatusCodeValue());
    }

    @Test
    void testUpdateCourse() {
        when(courseRepository.findById("CS101")).thenReturn(Optional.of(mockCourse));
        when(courseMapper.toEntity(mockCourseDto)).thenReturn(mockCourse);
        when(courseRepository.save(mockCourse)).thenReturn(mockCourse);
        when(courseToElasticsearchMapper.toES(mockCourse)).thenReturn(null);
        when(courseSearchRepository.save(any())).thenReturn(null);

        ResponseEntity<?> response = courseService.updateCourse("CS101", mockCourseDto);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void testDeleteCourse() {
        when(courseRepository.existsById("CS101")).thenReturn(true);
        doNothing().when(courseRepository).deleteById("CS101");

        ResponseEntity<?> response = courseService.deleteCourse("CS101");
        assertNotNull(response);
        assertEquals(204, response.getStatusCodeValue());
    }
}
