package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;

import java.security.Timestamp;
import java.util.List;

public interface AnnouncementRepository extends AbstractEntityRepository<Announcement> {
    List<Announcement> findByCourse(Course course);
    List<Announcement> findByPostedBy(AbstractUser postedBy);
}
