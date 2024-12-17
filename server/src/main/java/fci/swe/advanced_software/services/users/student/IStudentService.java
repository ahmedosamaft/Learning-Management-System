package fci.swe.advanced_software.services.users.student;

import fci.swe.advanced_software.dtos.assessments.submission.SubmissionRequestDto;
import fci.swe.advanced_software.dtos.course.EnrollmentDto;
import fci.swe.advanced_software.dtos.users.StudentRequestDto;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface IStudentService {
    ResponseEntity<?> getStudent(String id);

    ResponseEntity<?> enrollCourse(EnrollmentDto enrollmentDto);

    ResponseEntity<?> getCourses();

    ResponseEntity<?> searchCourses(String keyword);

    ResponseEntity<?> getAttendance();

    ResponseEntity<?> getCourseAttendance(String courseId);

    ResponseEntity<?> attendLesson(String lessonId, String otp);

    ResponseEntity<?> getFeedbacks(AssessmentType assessmentType);

    ResponseEntity<?> getCourseFeedbacks(AssessmentType assessmentType, String courseId);

    ResponseEntity<?> getReports();

    ResponseEntity<?> comment(String announcementId, String comment);

    ResponseEntity<?> updateProfile(StudentRequestDto requestDto);
}
