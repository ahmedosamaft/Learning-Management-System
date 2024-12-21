package fci.swe.advanced_software.services.auth;

import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.EnrollmentRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component("authorizationService")
@RequiredArgsConstructor
public class AuthorizationService {
    private final EnrollmentRepository enrollmentRepository;
    private final AuthUtils authUtils;
    private final CourseRepository courseRepository;

    public boolean isEnrolled(String courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found!");
        }
        String studentId = authUtils.getCurrentUserId();
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }

    public boolean isTeaching(String courseId) {
        if (!courseRepository.existsById(courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found!");
        }
        String instructorId = authUtils.getCurrentUserId();
        return courseRepository.existsByInstructorId(instructorId);
    }
}
