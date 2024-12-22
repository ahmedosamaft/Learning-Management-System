package fci.swe.advanced_software.services.auth;

import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.assessments.AttemptRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.EnrollmentRepository;
import fci.swe.advanced_software.repositories.course.LessonRepository;
import fci.swe.advanced_software.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Component("authorizationService")
@RequiredArgsConstructor
public class AuthorizationService {
    private final EnrollmentRepository enrollmentRepository;
    private final AuthUtils authUtils;
    private final CourseRepository courseRepository;
    private final AssessmentRepository assessmentRepository;
    private final LessonRepository lessonRepository;
    private final AttemptRepository attemptRepository;

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
        return courseRepository.existsByIdAndInstructorId(courseId, instructorId);
    }

    public boolean containsAssessment(String courseId, String assessmentId, String typeName) {
        if (!assessmentRepository.existsByIdAndCourseId(assessmentId, courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, typeName + " not found!");
        }
        return true;
    }

    public boolean containsLesson(String courseId, String lessonId) {
        if (!lessonRepository.existsByIdAndCourseId(lessonId, courseId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found!");
        }
        return true;
    }

    public boolean isSameType(String assessmentId, String typeName) {
        Assessment assessment = assessmentRepository.findById(assessmentId).orElse(null);
        if (assessment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, typeName + " not found!");
        }
        if (!assessment.getType().name().equalsIgnoreCase(typeName)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid " + typeName + " ID");
        }
        return true;
    }

    public boolean containsAttempt(String assessmentId, String attemptId) {
        if (!attemptRepository.existsByIdAndAssessmentId(attemptId, assessmentId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempt not found!");
        }
        return true;
    }
}
