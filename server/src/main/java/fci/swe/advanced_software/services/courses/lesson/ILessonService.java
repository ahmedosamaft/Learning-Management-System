package fci.swe.advanced_software.services.courses.lesson;

import fci.swe.advanced_software.dtos.course.LessonDto;
import org.springframework.http.ResponseEntity;

public interface ILessonService {
    ResponseEntity<?> getAllLessons(String courseId, Integer page, Integer size);

    ResponseEntity<?> getLessonById(String id);

    ResponseEntity<?> createLesson(LessonDto lessonDto, String courseId);

    ResponseEntity<?> updateLesson(String id, LessonDto lessonDto);

    ResponseEntity<?> deleteLesson(String id);
}
