package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Stream;

import static org.hibernate.annotations.QueryHints.FETCH_SIZE;
import static org.hibernate.annotations.QueryHints.READ_ONLY;

@Repository
public interface EnrollmentRepository extends AbstractEntityRepository<Enrollment> {
    Page<Enrollment> findAllByStudent(Student student, Pageable pageable);

    Page<Enrollment> findAllByCourseId(String courseId, Pageable pageable);

    List<Enrollment> findAllByCourseId(String courseId);

    Enrollment findByStudentAndCourse(Student student, Course course);

    boolean existsByStudentIdAndCourseId(String studentId, String courseId);

    @Query(value = "SELECT * FROM enrollment WHERE course_id = :courseId", nativeQuery = true)
    @QueryHints(value = {
            @QueryHint(name = FETCH_SIZE, value = "1000"),
            @QueryHint(name = READ_ONLY, value = "true")
    })
    Stream<Enrollment> findAllByCourseIdWithCursor(String courseId);

    long countByCourseId(String courseId);
}
