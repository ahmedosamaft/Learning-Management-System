package fci.swe.advanced_software.services.auth;

import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.EnrollmentRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component( "authorizationService")
@RequiredArgsConstructor
public class AuthorizationService {
    private final EnrollmentRepository enrollmentRepository;
    private final AuthUtils authUtils;
    private final CourseRepository courseRepository;

    public boolean isEnrolled(String courseId) {
        String studentId = authUtils.getCurrentUserId();
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }

    public boolean isTeaching(String courseId) {
        String instructorId = authUtils.getCurrentUserId();
        return courseRepository.existsByInstructorId(instructorId);
    }

}
