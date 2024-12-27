package fci.swe.advanced_software.services.users;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.services.users.instructor.InstructorService;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.mappers.courses.CourseMapper;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstructorServiceTests {

    @InjectMocks
    private InstructorService instructorService;

    @Mock
    private InstructorRepository instructorRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AuthUtils authUtils;

    @Mock
    private CourseMapper courseMapper;

    @Mock
    private UserResponseMapper userResponseMapper;

    @Mock
    private RepositoryUtils repositoryUtils;

    @Test
    public void getInstructor_Found_ShouldReturnInstructor() {
        Instructor instructor = Instructor.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .name(instructor.getName())
                .email(instructor.getEmail())
                .role(instructor.getRole())
                .build();

        when(instructorRepository.findById(instructor.getId())).thenReturn(Optional.of(instructor));
        when(userResponseMapper.toDto(any())).thenReturn(userResponseDto);

        ResponseEntity<Response> response = instructorService.getInstructor(instructor.getId());

        verify(instructorRepository, times(1)).findById(instructor.getId());
        verify(userResponseMapper, times(1)).toDto(instructor);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Instructor retrieved successfully!", response.getBody().getMessage());
        assertEquals(Map.of("instructor", userResponseDto), response.getBody().getData());
    }

    @Test
    public void getInstructor_NotFound_ShouldReturnNotFound() {
        String id = UUID.randomUUID().toString();

        when(instructorRepository.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<Response> response = instructorService.getInstructor(id);

        verify(instructorRepository, times(1)).findById(id);
        verify(userResponseMapper, never()).toDto(any());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Instructor not found!", response.getBody().getMessage());
    }

    @Test
    public void getAllInstructors_ShouldReturnInstructors() {
        Integer page = 1;
        Integer size = 10;

        Instructor instructor = Instructor.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        UserResponseDto userResponseDto = UserResponseDto.builder()
                .name(instructor.getName())
                .email(instructor.getEmail())
                .role(instructor.getRole())
                .build();

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");

        Page<Instructor> instructors = new PageImpl<>(List.of(instructor), pageable, 1);

        when(repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt")).thenReturn(pageable);
        when(instructorRepository.findAll(pageable)).thenReturn(instructors);
        when(userResponseMapper.toDto(any())).thenReturn(userResponseDto);

        ResponseEntity<Response> response = instructorService.getAllInstructors(page, size);

        verify(repositoryUtils, times(1)).getPageable(page, size, Sort.Direction.ASC, "createdAt");
        verify(instructorRepository, times(1)).findAll(pageable);
        verify(userResponseMapper, times(1)).toDto(instructor);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Instructors retrieved successfully!", response.getBody().getMessage());
        assertEquals(Map.of("instructors", instructors.map(userResponseMapper::toDto).getContent()), response.getBody().getData());
    }

    @Test
    public void getCourses_ShouldReturnCourses() {
        Integer page = 1;
        Integer size = 10;

        Instructor instructor = Instructor.builder()
                .id(UUID.randomUUID().toString())
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        Course course = Course.builder()
                .id(UUID.randomUUID().toString())
                .code("TS101")
                .name("test")
                .description("only for testing")
                .instructor(instructor)
                .build();

        CourseDto courseDto = CourseDto.builder()
                .code(course.getCode())
                .name(course.getName())
                .description(course.getDescription())
                .build();

        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
        Page<Course> courses = new PageImpl<>(List.of(course), pageable, 1);

        when(authUtils.getCurrentUserId()).thenReturn(instructor.getId());
        when(repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt")).thenReturn(pageable);
        when(courseRepository.findAllByInstructorId(instructor.getId(), pageable)).thenReturn(courses);
        when(courseMapper.toDto(any())).thenReturn(courseDto);

        ResponseEntity<Response> response = instructorService.getCourses(page, size);

        verify(authUtils, times(1)).getCurrentUserId();
        verify(repositoryUtils, times(1)).getPageable(page, size, Sort.Direction.ASC, "createdAt");
        verify(courseRepository, times(1)).findAllByInstructorId(instructor.getId(), pageable);
        verify(courseMapper, times(1)).toDto(course);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Courses retrieved successfully!", response.getBody().getMessage());
        assertEquals(Map.of("courses", courses.map(courseMapper::toDto).getContent()), response.getBody().getData());
    }
}
