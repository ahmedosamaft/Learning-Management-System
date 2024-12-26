package fci.swe.advanced_software.services.users.student;

import fci.swe.advanced_software.dtos.Response;
import org.springframework.http.ResponseEntity;

public interface IStudentService {
    ResponseEntity<Response> enrollCourse(String courseId, String StudentId);

    ResponseEntity<Response> dropCourse(String courseId, String StudentId);

    ResponseEntity<Response> getCourses(Integer page, Integer size);

    ResponseEntity<Response> searchCourses(String keyword, Integer page, Integer size);

    ResponseEntity<Response> attendLesson(String lessonId, String otp);
}
