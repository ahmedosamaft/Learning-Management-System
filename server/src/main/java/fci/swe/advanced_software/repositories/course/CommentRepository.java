package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.Comment;
import fci.swe.advanced_software.models.users.AbstractUser;
import fci.swe.advanced_software.repositories.AbstractEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends AbstractEntityRepository<Comment> {
    Page<Comment> findByAnnouncement(Announcement announcement, Pageable pageable);

    Page<Comment> findByAuthorId(String authorId, Pageable pageable);

    Page<Comment> findByAuthor(AbstractUser Author, Pageable pageable);

    Page<Comment> findByAnnouncementAndAuthor(Announcement announcementId, AbstractUser Author, Pageable pageable);
}

