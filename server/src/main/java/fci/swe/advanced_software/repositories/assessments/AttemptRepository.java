package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends AbstractEntityRepository<Attempt> {

    List<Attempt> findByAssessment(Assessment assessment);

    List<Attempt> findByStudent(Student student);

    List<Attempt> findByAssessmentAndStudent(Assessment assessment, Student student);
}
