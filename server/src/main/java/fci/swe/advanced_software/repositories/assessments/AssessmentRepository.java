package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends AbstractEntityRepository<Assessment> {
    List<Assessment> findAllByCourse(Course course);

    List<Assessment> findAllByCourseAndType(Course course, AssessmentType type);

    Page<Assessment> findAllByCourseIdAndType(String course_id, AssessmentType type, Pageable pageable);

    boolean existsByIdAndCourseId(String assessmentId, String courseId);

    List<Assessment> findAllByCourseId(String courseId);
}
