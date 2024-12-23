package fci.swe.advanced_software.services.courses.course;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.dtos.course.CourseSearchDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.CourseSearchRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.Constants;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.courses.CourseMapper;
import fci.swe.advanced_software.utils.mappers.courses.CourseToElasticsearchMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    private final CourseMapper courseMapper;
    private final AuthUtils authUtils;
    private final CourseSearchRepository courseSearchRepository;
    private final CourseToElasticsearchMapper courseToElasticsearchMapper;

    @Override
    public ResponseEntity<?> getAllCourses(Pageable pageable) {
        Page<Course> coursePage = courseRepository.findAll(pageable);

        List<CourseDto> courses = coursePage.stream()
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
                        .withData("course", courseMapper.toDto(course))
                        .build())
                .orElseGet(() -> ResponseEntityBuilder.create()
                        .withStatus(HttpStatus.NOT_FOUND)
                        .withMessage("Course not found")
                        .build());
    }

    @Override
    public ResponseEntity<?> createCourse(CourseDto courseDto) {

        courseDto.setInstructorId(authUtils.getCurrentUserId());
        Course course = courseMapper.toEntity(courseDto);
        Instructor instructor = instructorRepository.findById(authUtils.getCurrentUserId()).orElse(null);

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

        if(courseDto.getInstructorId() == null) {
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
