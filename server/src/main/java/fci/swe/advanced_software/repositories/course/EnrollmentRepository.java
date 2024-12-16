package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EnrollmentRepository extends AbstractEntityRepository<Enrollment> {
    List<Enrollment> findByStudent(Student student);

    List<Enrollment> findByCourse(Course course);

    Enrollment findByStudentAndCourse(Student student, Course course);
}
