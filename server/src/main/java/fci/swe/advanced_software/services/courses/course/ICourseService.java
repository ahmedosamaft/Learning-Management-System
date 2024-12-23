package fci.swe.advanced_software.services.courses.course;

import fci.swe.advanced_software.dtos.course.CourseDto;
import org.springframework.http.ResponseEntity;

public interface ICourseService {
    ResponseEntity<?> getAllCourses(Integer page, Integer size);

    ResponseEntity<?> getCourseById(String id);

    ResponseEntity<?> createCourse(CourseDto courseDto);

    ResponseEntity<?> updateCourse(String id, CourseDto courseDto);

    ResponseEntity<?> deleteCourse(String name);
}