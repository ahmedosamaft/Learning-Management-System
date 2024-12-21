package fci.swe.advanced_software.services.users.instructor;

import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.courses.CourseMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class InstructorService implements IInstructorService {

    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final AuthUtils authUtils;
    private final CourseMapper courseMapper;
    private final RepositoryUtils repositoryUtils;

    @Override
    public ResponseEntity<?> getInstructor(String id) {
        Instructor instructor = instructorRepository.findById(id).orElse(null);
        if (instructor == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Instructor not found!")
                    .build();
        }

        // userDto instead of studentDto
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Instructor retrieved successfully!")
                .withData("instructor", instructor)
                .build();
    }

    @Override
    public ResponseEntity<?> getCourses(Integer page, Integer size) {
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Course> coursesPage = courseRepository.findAllByInstructorId(authUtils.getCurrentUserId(), pageable);
        List<CourseDto> coursesDto = coursesPage.map(courseMapper::toDto).getContent();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Courses retrieved successfully!")
                .withData("courses", coursesDto)
                .build();
    }

    private Instructor validateAndRetrieveCurrentInstructor() {
        Instructor instructor = instructorRepository.findById(authUtils.getCurrentUserId()).orElse(null);
        if (instructor == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not a instructor!");
        }
        return instructor;
    }
}
