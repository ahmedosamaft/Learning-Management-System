package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnnouncementRepository extends AbstractEntityRepository<Announcement> {
    List<Announcement> findByCourse(Course course);
    List<Announcement> findByPostedBy(AbstractUser postedBy);
    Page<Announcement> findByCourseId(String courseId, Pageable pageable);
}
