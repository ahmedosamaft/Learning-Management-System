package fci.swe.advanced_software.services.courses.lesson;

import fci.swe.advanced_software.dtos.course.LessonDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface ILessonService {
    ResponseEntity<?> getAllLessons(Pageable pageable);

    ResponseEntity<?> getLessonById(String id);

    ResponseEntity<?> createLesson(LessonDto lessonDto);

    ResponseEntity<?> updateLesson(String id, LessonDto lessonDto);

    ResponseEntity<?> deleteLesson(String id);
}
