package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Answer;
import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnswerRepository extends AbstractEntityRepository<Answer> {
    List<Answer> findAllByAttempt(Attempt attempt);

    List<Answer> findByAssessment(Assessment assessment);

    List<Answer> findByStudent(Student student);

    List<Answer> findByQuestion(Question question);
    Optional<Answer> findByAttemptAndStudent(Attempt attempt, Student student);
}
