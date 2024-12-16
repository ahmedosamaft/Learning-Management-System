package fci.swe.advanced_software.services.users.student;

import fci.swe.advanced_software.dtos.course.EnrollmentDto;
import fci.swe.advanced_software.dtos.users.StudentRequestDto;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import org.springframework.http.ResponseEntity;

public interface IStudentService {
    ResponseEntity<?> getStudent(String id);

    ResponseEntity<?> enrollCourse(EnrollmentDto enrollmentDto);

    ResponseEntity<?> getGrades(String courseId);

    ResponseEntity<?> getCourses();

    ResponseEntity<?> searchCourses(String keyword);

    ResponseEntity<?> getAttendance(String courseId);

    ResponseEntity<?> attendLesson(String lessonId, String otp);

    ResponseEntity<?> getAssessments(AssessmentType type);

    ResponseEntity<?> getCourseAssessments(String courseId, AssessmentType type);

    ResponseEntity<?> attemptAssessment(String assessmentId);

    ResponseEntity<?> getFeedbacks();

    ResponseEntity<?> getCourseFeedbacks(String courseId);

    ResponseEntity<?> getReports();

    ResponseEntity<?> comment(String announcementId, String comment);

    ResponseEntity<?> updateProfile(StudentRequestDto requestDto);
}
