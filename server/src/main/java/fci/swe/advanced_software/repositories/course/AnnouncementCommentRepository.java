package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.AnnouncementComment;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface AnnouncementCommentRepository extends AbstractEntityRepository<AnnouncementComment> {
    List<AnnouncementComment> findByAnnouncement(Announcement announcement);

    List<AnnouncementComment> findByCommenter(Student commenter);

    List<AnnouncementComment> findByAnnouncementAndCommenter(Announcement announcementId, Student commenterId);
}

