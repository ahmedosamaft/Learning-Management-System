package fci.swe.advanced_software.services.users.student;

import fci.swe.advanced_software.dtos.users.StudentRequestDto;
import org.springframework.http.ResponseEntity;

public interface IStudentService {
    ResponseEntity<?> getStudent(String id);

    ResponseEntity<?> courseEnroll(StudentRequestDto requestDto);

    ResponseEntity<?> attendLecture(String studentId, String lectureId);

    ResponseEntity<?> attemptAssessment(String studentId, String assessmentId);

    ResponseEntity<?> generatePerformanceReport(String studentId);

    ResponseEntity<?> requestFeedback(String studentId, String assessmentId);

    ResponseEntity<?> commentOnAnnouncement(String studentId, String announcementId, String comment);

    ResponseEntity<?> getStudentCourses(String studentId);

    ResponseEntity<?> getStudentLectures(String studentId);

    ResponseEntity<?> getStudentAssessments(String studentId);

    ResponseEntity<?> getStudentFeedbacks(String studentId);

    ResponseEntity<?> getStudentReports(String studentId);
}
