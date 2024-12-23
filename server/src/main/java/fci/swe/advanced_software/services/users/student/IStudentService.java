package fci.swe.advanced_software.services.users.student;

import org.springframework.http.ResponseEntity;

public interface IStudentService {
    ResponseEntity<?> enrollCourse(String courseId, String StudentId);

    ResponseEntity<?> dropCourse(String courseId, String StudentId);

    ResponseEntity<?> getCourses(Integer page, Integer size);

    ResponseEntity<?> searchCourses(String keyword, Integer page, Integer size);

    ResponseEntity<?> attendLesson(String lessonId, String otp);
}
