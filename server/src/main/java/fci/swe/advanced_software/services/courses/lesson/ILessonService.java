package fci.swe.advanced_software.services.courses.lesson;

import fci.swe.advanced_software.dtos.course.LessonDto;
import org.springframework.http.ResponseEntity;

public interface ILessonService {
    ResponseEntity<?> getAllLessons(String course_id, Integer page, Integer size);

    ResponseEntity<?> getLessonById(String id);

    ResponseEntity<?> createLesson(LessonDto lessonDto, String course_id);

    ResponseEntity<?> updateLesson(String id, LessonDto lessonDto);

    ResponseEntity<?> deleteLesson(String id);
}
