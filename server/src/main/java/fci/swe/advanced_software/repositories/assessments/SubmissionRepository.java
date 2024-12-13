package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Question;
import fci.swe.advanced_software.models.assessments.Submission;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends AbstractEntityRepository<Submission> {
    List<Submission> findByAttempt(Attempt attempt);

    List<Submission> findByAssessment(Assessment assessment);

    List<Submission> findByStudent(Student student);

    List<Submission> findByQuestion(Question question);
    Optional<Submission> findByAttemptAndStudent(Attempt attempt, Student student);
}
