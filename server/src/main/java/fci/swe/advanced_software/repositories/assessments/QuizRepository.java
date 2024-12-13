package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Quiz;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends AbstractEntityRepository<Quiz> {
}
