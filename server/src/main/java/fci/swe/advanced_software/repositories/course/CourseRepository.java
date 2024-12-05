package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends AbstractEntityRepository<Course> {
    List<Course> findByInstructor(Instructor instructor);

    Course findByCode(String code);

    boolean existsByName(String name);
}
