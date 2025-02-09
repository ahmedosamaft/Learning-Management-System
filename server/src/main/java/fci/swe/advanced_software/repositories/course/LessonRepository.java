package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends AbstractEntityRepository<Lesson> {
    List<Lesson> findAllByCourse(Course course);

    Page<Lesson> findAllByCourseId(String courseId, Pageable pageable);

    boolean existsByIdAndCourseId(String id, String courseId);
}
