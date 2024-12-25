package fci.swe.advanced_software.services.users;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.course.*;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import fci.swe.advanced_software.services.users.student.StudentService;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.mappers.courses.AttendanceMapper;
import fci.swe.advanced_software.utils.mappers.courses.CourseMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTests {

    @InjectMocks
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private EnrollmentRepository enrollmentRepository;

    @Mock
    private AttendanceRepository attendanceRepository;

    @Mock
    private LessonRepository lessonRepository;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private AttendanceMapper attendanceMapper;

    @Mock
    private AuthUtils authUtils;

    @Mock
    private RepositoryUtils repositoryUtils;

    @Mock
    private CourseSearchRepository courseSearchRepository;

    @Test
    public void enrollCourse_StudentFoundAndNotEnrolled_ShouldReturnBadRequest() {

        Course course = Course.builder()
                .id(UUID.randomUUID().toString())
                .code("CS101")
                .name("cs")
                .description("desc")
                .build();

        Student student = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId())).thenReturn(false);
        when(enrollmentRepository.save(any())).thenReturn(null);

        ResponseEntity<Response> response = studentService.enrollCourse(course.getId(), student.getId());

        verify(studentRepository, times(1)).findById(student.getId());
        verify(courseRepository, times(1)).findById(course.getId());
        verify(enrollmentRepository, times(1)).existsByStudentIdAndCourseId(student.getId(), course.getId());
        verify(enrollmentRepository, times(1)).save(any());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Course enrolled successfully!", response.getBody().getMessage());
    }

    @Test
    public void enrollCourse_StudentFoundAndAlreadyEnrolled_ShouldReturnBadRequest() {

        Course course = Course.builder()
                .id(UUID.randomUUID().toString())
                .code("CS101")
                .name("cs")
                .description("desc")
                .build();

        Student student = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId())).thenReturn(true);

        ResponseEntity<Response> response = studentService.enrollCourse(course.getId(), student.getId());

        verify(studentRepository, times(1)).findById(student.getId());
        verify(courseRepository, times(1)).findById(course.getId());
        verify(enrollmentRepository, times(1)).existsByStudentIdAndCourseId(student.getId(), course.getId());
        verify(enrollmentRepository, never()).save(any());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("You are already enrolled in this course!", response.getBody().getMessage());
    }

    @Test
    public void enrollCourse_StudentNotFound_ShouldReturnNotFound() {

        String id = UUID.randomUUID().toString();

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            studentService.enrollCourse(id, id);
        });

        verify(studentRepository, times(1)).findById(id);
        verify(courseRepository, never()).findById(any());
        verify(enrollmentRepository, never()).existsByStudentIdAndCourseId(any(), any());
        verify(enrollmentRepository, never()).save(any());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Student not found!", exception.getReason());
    }

    @Test
    public void dropCourse_StudentFoundAndEnrolled_ShouldReturnNoContent() {

        Course course = Course.builder()
                .id(UUID.randomUUID().toString())
                .code("CS101")
                .name("cs")
                .description("desc")
                .build();

        Student student = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        Enrollment enrollment = Enrollment.builder()
                .student(student)
                .course(course)
                .build();

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(enrollment);

        ResponseEntity<Response> response = studentService.dropCourse(course.getId(), student.getId());

        verify(studentRepository, times(1)).findById(student.getId());
        verify(courseRepository, times(1)).findById(course.getId());
        verify(enrollmentRepository, times(1)).findByStudentAndCourse(student, course);
        verify(enrollmentRepository, times(1)).delete(any());
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals("Course dropped successfully!", response.getBody().getMessage());
    }

    @Test
    public void dropCourse_StudentFoundAndNotEnrolled_ShouldReturnNotFound() {

        Course course = Course.builder()
                .id(UUID.randomUUID().toString())
                .code("CS101")
                .name("cs")
                .description("desc")
                .build();

        Student student = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(courseRepository.findById(course.getId())).thenReturn(Optional.of(course));
        when(enrollmentRepository.findByStudentAndCourse(student, course)).thenReturn(null);

        ResponseEntity<Response> response = studentService.dropCourse(course.getId(), student.getId());

        verify(studentRepository, times(1)).findById(student.getId());
        verify(courseRepository, times(1)).findById(course.getId());
        verify(enrollmentRepository, times(1)).findByStudentAndCourse(student, course);
        verify(enrollmentRepository, never()).delete(any());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Course not found!", response.getBody().getMessage());
    }

    @Test
    public void dropCourse_StudentNotFound_ShouldReturnNotFound() {

        String id = UUID.randomUUID().toString();

        when(studentRepository.findById(id)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            studentService.dropCourse(id, id);
        });

        verify(studentRepository, times(1)).findById(id);
        verify(courseRepository, never()).findById(any());
        verify(enrollmentRepository, never()).findByStudentAndCourse(any(), any());
        verify(enrollmentRepository, never()).delete(any());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Student not found!", exception.getReason());
    }

    @Test
    public void getCourses_ShouldReturnCourses() {
        Integer page = 1;
        Integer size = 10;

        Student student = Student.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        Course course = Course.builder()
                .id(UUID.randomUUID().toString())
                .code("CS101")
                .name("cs")
                .description("desc")
                .build();

        Enrollment enrollment = Enrollment.builder()
                .course(course)
                .student(student)
                .build();

        CourseDto courseDto = CourseDto.builder()
                .code("CS101")
                .name("cs")
                .description("desc")
                .build();

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");

        Page<Enrollment> enrollments = new PageImpl<>(List.of(enrollment), pageable, 1);

        when(authUtils.getCurrentUserId()).thenReturn(student.getId());
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt")).thenReturn(pageable);
        when(enrollmentRepository.findAllByStudent(student, pageable)).thenReturn(enrollments);
        when(courseMapper.toDto(any())).thenReturn(courseDto);

        ResponseEntity<Response> response = studentService.getCourses(page, size);

        verify(repositoryUtils, times(1)).getPageable(page, size, Sort.Direction.ASC, "createdAt");
        verify(enrollmentRepository, times(1)).findAllByStudent(student, pageable);
        verify(courseMapper, times(1)).toDto(course);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Courses retrieved successfully!", response.getBody().getMessage());
        assertEquals(Map.of("courses", List.of(courseDto)), response.getBody().getData());
    }
}