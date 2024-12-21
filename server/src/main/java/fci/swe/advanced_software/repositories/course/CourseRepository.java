package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends AbstractEntityRepository<Course> {
    Page<Course> findAllByInstructorId(String instructorId, Pageable pageable);

    boolean existsByInstructorId(String instructorId);
}
