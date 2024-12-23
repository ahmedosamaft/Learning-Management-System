package fci.swe.advanced_software.services.courses.course;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.dtos.course.CourseSearchDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.CourseSearchRepository;
import fci.swe.advanced_software.repositories.course.EnrollmentRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.courses.CourseMapper;
import fci.swe.advanced_software.utils.mappers.courses.CourseToElasticsearchMapper;
import fci.swe.advanced_software.utils.mappers.users.UserResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CourseMapper courseMapper;
    private final AuthUtils authUtils;
    private final CourseSearchRepository courseSearchRepository;
    private final CourseToElasticsearchMapper courseToElasticsearchMapper;
    private final RepositoryUtils repositoryUtils;
    private final EnrollmentRepository enrollmentRepository;
    private final UserResponseMapper userResponseMapper;

    @Override
    public ResponseEntity<?> getAllCourses(Integer page, Integer size) {
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Course> courses = courseRepository.findAll(pageable);

        List<CourseDto> response = courses
                .map(courseMapper::toDto)
                .toList();

        return buildSuccessResponse("Courses retrieved successfully", response, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCourseById(String id) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        return courseOpt
                .map(course -> ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .withMessage("Course retrieved successfully")
                        .withData("course", courseMapper.toDto(course))
                        .build())
                .orElseGet(() -> ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.NOT_FOUND)
                        .withMessage("Course not found")
                        .build());
    }

    @Override
    public ResponseEntity<?> createCourse(CourseDto courseDto) {

        Course course = courseMapper.toEntity(courseDto);

        Instructor instructor = course.getInstructor();

        Course savedCourse = courseRepository.save(course);
        CourseSearchDto esCourse = courseToElasticsearchMapper.toES(savedCourse);
        courseSearchRepository.save(esCourse);
        String location = Constants.API_VERSION + "/courses/" + savedCourse.getId();

        courseDto = courseMapper.toDto(savedCourse);
        courseDto.setInstructorId(instructor.getName());

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withMessage("Course created successfully")
                .withData("course", courseDto)
                .withLocation(location)
                .build();
    }

    @Override
    public ResponseEntity<?> updateCourse(String id, CourseDto courseDto) {
        Course course = courseRepository.findById(id).orElse(null);

        if (course == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Course not found")
                    .build();
        }

        if (courseDto.getInstructorId() == null) {
            courseDto.setInstructorId(course.getInstructor().getId());
        }

        courseMapper.updateEntityFromDto(courseDto, course);

        Course updatedCourse = courseRepository.save(course);

        courseSearchRepository.deleteById(id);
        CourseSearchDto esCourse = courseToElasticsearchMapper.toES(updatedCourse);
        courseSearchRepository.save(esCourse);

        return buildSuccessResponse("Course updated successfully", courseMapper.toDto(updatedCourse), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> deleteCourse(String id) {
        if (!courseRepository.existsById(id)) {
            return createErrorResponse("Course not found", HttpStatus.NOT_FOUND);
        }

        courseRepository.deleteById(id);
        courseSearchRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<?> getStudents(String courseId, Integer page, Integer size) {
        Optional<Course> courseOpt = courseRepository.findById(courseId);

        if (courseOpt.isEmpty()) {
            return createErrorResponse("Course not found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOpt.get();
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Enrollment> enrollments = enrollmentRepository.findAllByCourseId(courseId, pageable);

        List<UserResponseDto> students = enrollments.stream()
                .map(enrollment -> userResponseMapper.toDto(enrollment.getStudent())).toList();

        return buildSuccessResponse("Students retrieved successfully", students, HttpStatus.OK);
    }

    // Helper method for success responses
    private ResponseEntity<?> buildSuccessResponse(String message, Object data, HttpStatus status) {
        return ResponseEntityBuilder.create()
                .withStatus(status)
                .withMessage(message)
                .withData("course", data)
                .build();
    }

    // Helper method for error responses
    private ResponseEntity<?> createErrorResponse(String message, HttpStatus status) {
        return ResponseEntityBuilder.create()
                .withStatus(status)
                .withMessage(message)
                .build();
    }
}
