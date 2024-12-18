package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends AbstractEntityRepository<Question> {
    List<Question> findByCourse(Course course);
}
