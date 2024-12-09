package fci.swe.advanced_software.services.courses.course;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    @Override
    public ResponseEntity<?> getAllCourses() {
        List<CourseDto> courses = courseRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return buildSuccessResponse("Courses retrieved successfully", courses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCourseById(String id) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        return courseOpt.map(course -> ResponseEntityBuilder.<CourseDto>create()
                        .withStatus(HttpStatus.OK)
                        .withMessage("Course retrieved successfully")
                        .withData(convertToDto(course))
                        .build())
                .orElseGet(() -> ResponseEntityBuilder.<String>create()
                        .withStatus(HttpStatus.NOT_FOUND)
                        .withMessage("Course not found")
                        .build());

    }

    @Override
    public ResponseEntity<?> createCourse(CourseDto courseDto) {
        Optional<Instructor> instructorOpt = instructorRepository.findById(courseDto.getInstructorId());
        if (instructorOpt.isEmpty()) {
            return createErrorResponse("Instructor not found", HttpStatus.NOT_FOUND);
        }

        Course course = new Course();
        course.setCode(courseDto.getCode());
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setInstructor(instructorOpt.get());

        Course savedCourse = courseRepository.save(course);

        return buildSuccessResponse("Course created successfully", convertToDto(savedCourse), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<?> updateCourse(String id, CourseDto courseDto) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        if (courseOpt.isEmpty()) {
            return createErrorResponse("Course not found", HttpStatus.NOT_FOUND);
        }

        Optional<Instructor> instructorOpt = instructorRepository.findById(courseDto.getInstructorId());
        if (instructorOpt.isEmpty()) {
            return createErrorResponse("Instructor not found", HttpStatus.NOT_FOUND);
        }

        Course course = courseOpt.get();
        course.setCode(courseDto.getCode());
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());
        course.setInstructor(instructorOpt.get());

        Course updatedCourse = courseRepository.save(course);

        return buildSuccessResponse("Course updated successfully", convertToDto(updatedCourse), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> deleteCourse(String id) {
        if (!courseRepository.existsById(id)) {
            return createErrorResponse("Course not found", HttpStatus.NOT_FOUND);
        }

        courseRepository.deleteById(id);

        return buildSuccessResponse("Course deleted successfully", null, HttpStatus.NO_CONTENT);
    }

    private CourseDto convertToDto(Course course) {
        return new CourseDto(
                course.getCode(),
                course.getId(),
                course.getName(),
                course.getDescription(),
                course.getInstructor().getId(),
                course.getInstructor().getName()
        );
    }


    // Helper method for success responses
    private ResponseEntity<?> buildSuccessResponse(String message, Object data, HttpStatus status) {
        return ResponseEntityBuilder.create()
                .withStatus(status)
                .withMessage(message)
                .withData(data)
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
