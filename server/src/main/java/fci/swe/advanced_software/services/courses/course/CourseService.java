package fci.swe.advanced_software.services.courses.course;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.courses.CourseMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService implements ICourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;
    private final CourseMapper courseMapper;

    @Override
    public ResponseEntity<?> getAllCourses() {
        List<CourseDto> courses = courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());

        return buildSuccessResponse("Courses retrieved successfully", courses, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getCourseById(String id) {
        Optional<Course> courseOpt = courseRepository.findById(id);
        return courseOpt
                .map(course -> ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.OK)
                        .withMessage("Course retrieved successfully")
                        .withData(courseMapper.toDto(course))
                        .build())
                .orElseGet(() -> ResponseEntityBuilder.create()
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

        Course course = courseMapper.toEntity(courseDto);
        course.setInstructor(instructorOpt.get());

        Course savedCourse = courseRepository.save(course);

        String location = Constants.API_VERSION + "/courses/" + savedCourse.getId();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.CREATED)
                .withMessage("Course created successfully")
                .withData(courseMapper.toDto(savedCourse))
                .withLocation(location)
                .build();
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

        // Get the existing course
        Course existingCourse = courseOpt.get();

        // Use the mapper to update fields of the existing entity
        courseMapper.updateEntityFromDto(courseDto, existingCourse);
        existingCourse.setInstructor(instructorOpt.get());

        // Save the updated course
        Course updatedCourse = courseRepository.save(existingCourse);

        return buildSuccessResponse("Course updated successfully", courseMapper.toDto(updatedCourse), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<?> deleteCourse(String id) {
        if (!courseRepository.existsById(id)) {
            return createErrorResponse("Course not found", HttpStatus.NOT_FOUND);
        }

        courseRepository.deleteById(id);

        return buildSuccessResponse("Course deleted successfully", null, HttpStatus.NO_CONTENT);
    }

    // Helper method for success responses
    private ResponseEntity<?> buildSuccessResponse(String message, Object data, HttpStatus status) {
        Map<String, Object> wrappedData = data != null ? Collections.singletonMap("course", data) : null;

        return ResponseEntityBuilder.create()
                .withStatus(status)
                .withMessage(message)
                .withData(wrappedData)
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
