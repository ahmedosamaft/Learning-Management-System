package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Assessment;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttemptRepository extends AbstractEntityRepository<Attempt> {

    Page<Attempt> findAllByAssessmentId(String Id, Pageable pageable);

    List<Attempt> findAllByStudent(Student student);

    List<Attempt> findAllByStudentId(String studentId);

    Attempt findByAssessmentAndStudent(Assessment assessment, Student student);

    boolean existsByStudentIdAndAssessmentId(String studentId, String assessmentId);

    List<Attempt> findAllByAssessmentAndStudentId(Assessment assessment, String studentId);

    @Query("SELECT a FROM Attempt a WHERE a.assessment.course.id = :courseId AND a.student.id = :studentId")
    Page<Attempt> findAllByCourseIdAndStudentId(@Param("courseId") String courseId,
                                                @Param("studentId") String studentId,
                                                Pageable pageable);
}
