package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Media;
import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.assessments.Assignment;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaRepository extends AbstractEntityRepository<Media> {
    List<Media> findByAnnouncement(Announcement announcement);
    List<Media> findByLesson(Lesson lesson);
    List<Media> findByAssignment(Assignment assignment);
}
