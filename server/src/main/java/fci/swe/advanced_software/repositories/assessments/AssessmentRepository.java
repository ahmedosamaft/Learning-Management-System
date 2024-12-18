package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.AssessmentType;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssessmentRepository extends AbstractEntityRepository<Assessment> {
    List<Assessment> findAllByCourse(Course course);

    List<Assessment> findAllByCourseAndType(Course course, AssessmentType type);
}
