package fci.swe.advanced_software.services.users.instructor;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.course.CourseDto;
import fci.swe.advanced_software.dtos.users.UserResponseDto;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import fci.swe.advanced_software.utils.RepositoryUtils;
import fci.swe.advanced_software.utils.ResponseEntityBuilder;
import fci.swe.advanced_software.utils.mappers.courses.CourseMapper;
import fci.swe.advanced_software.utils.mappers.users.UserResponseMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class InstructorService implements IInstructorService {

    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;
    private final AuthUtils authUtils;
    private final CourseMapper courseMapper;
    private final UserResponseMapper userResponseMapper;
    private final RepositoryUtils repositoryUtils;

    @Override
    public ResponseEntity<Response> getInstructor(String id) {
        Instructor instructor = instructorRepository.findById(id).orElse(null);
        if (instructor == null) {
            return ResponseEntityBuilder.create()
                    .withStatus(HttpStatus.NOT_FOUND)
                    .withMessage("Instructor not found!")
                    .build();
        }

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Instructor retrieved successfully!")
                .withData("instructor", userResponseMapper.toDto(instructor))
                .build();
    }

    @Override
    public ResponseEntity<Response> getAllInstructors(Integer page, Integer size) {
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Instructor> instructorsPage = instructorRepository.findAll(pageable);
        List<UserResponseDto> instructorsDto = instructorsPage.map(userResponseMapper::toDto).getContent();
        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Instructors retrieved successfully!")
                .withData("instructors", instructorsDto)
                .build();
    }

    @Override
    public ResponseEntity<Response> getCourses(Integer page, Integer size) {
        Pageable pageable = repositoryUtils.getPageable(page, size, Sort.Direction.ASC, "createdAt");
        Page<Course> coursesPage = courseRepository.findAllByInstructorId(authUtils.getCurrentUserId(), pageable);
        List<CourseDto> coursesDto = coursesPage.map(courseMapper::toDto).getContent();

        return ResponseEntityBuilder.create()
                .withStatus(HttpStatus.OK)
                .withMessage("Courses retrieved successfully!")
                .withData("courses", coursesDto)
                .build();
    }
}
