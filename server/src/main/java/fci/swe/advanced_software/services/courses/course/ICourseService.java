package fci.swe.advanced_software.services.courses.course;

import fci.swe.advanced_software.dtos.Response;
import fci.swe.advanced_software.dtos.course.CourseDto;
import org.springframework.http.ResponseEntity;

public interface ICourseService {
    ResponseEntity<Response> getAllCourses(Integer page, Integer size);

    ResponseEntity<Response> getCourseById(String id);

    ResponseEntity<Response> createCourse(CourseDto courseDto);

    ResponseEntity<Response> updateCourse(String id, CourseDto courseDto);

    ResponseEntity<Response> deleteCourse(String name);

    ResponseEntity<Response> getStudents(String course_id, Integer page, Integer size);
}