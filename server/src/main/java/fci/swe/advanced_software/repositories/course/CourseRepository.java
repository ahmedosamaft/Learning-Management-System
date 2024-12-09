package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends AbstractEntityRepository<Course> {
    List<Course> findByInstructor(Instructor instructor);

    Optional<Course> findById(String id);

    boolean existsById(String id);
}
