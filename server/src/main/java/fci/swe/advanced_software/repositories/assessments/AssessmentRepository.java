package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AssessmentRepository extends AbstractEntityRepository<Assessment> {
    List<Assessment> findByCourse(Course course);
}
