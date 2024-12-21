package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttemptRepository extends AbstractEntityRepository<Attempt> {

    List<Attempt> findAllByAssessmentId(String Id);

    List<Attempt> findAllByStudent(Student student);

    List<Attempt> findAllByStudentId(String studentId);

    Attempt findByAssessmentAndStudent(Assessment assessment, Student student);

    boolean existsByStudentIdAndAssessmentId(String studentId, String assessmentId);

    List<Attempt> findAllByAssessmentAndStudentId(Assessment assessment, String studentId);
}
