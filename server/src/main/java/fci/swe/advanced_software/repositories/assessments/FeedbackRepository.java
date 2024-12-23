package fci.swe.advanced_software.repositories.assessments;

import fci.swe.advanced_software.models.assessments.Feedback;
import fci.swe.advanced_software.models.assessments.Attempt;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeedbackRepository extends AbstractEntityRepository<Feedback> {
    Page<Feedback> findAllByCourseId(String courseId, Pageable pageable);

    List<Feedback> findByAttempt(Attempt attempt);

    List<Feedback> findByStudent(Student student);

    Optional<Feedback> findByAttemptAndStudent(Attempt attempt, Student student);

    List<Feedback> findByInstructor(Instructor instructor);
}
