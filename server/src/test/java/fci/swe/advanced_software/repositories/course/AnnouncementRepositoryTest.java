package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Announcement;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@ActiveProfiles("test")
class AnnouncementRepositoryTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindByCourse() {
        Instructor instructor = new Instructor();
        instructor.setName("Instructor Name");
        instructor.setEmail("instructor@example.com");
        instructor.setPassword("securepassword");
        instructor.setRole(Role.INSTRUCTOR);
        entityManager.persist(instructor);

        Course course = new Course();
        course.setCode("CS101");
        course.setName("Introduction to Computer Science");
        course.setDescription("This is a basic course in computer science.");
        course.setInstructor(instructor);
        entityManager.persist(course);

        Announcement announcement = new Announcement();
        announcement.setCourse(course);
        announcement.setPostedBy(instructor);
        announcement.setTitle("Midterm Exam");
        announcement.setContent("Details about the midterm exam...");
        announcement.setPostedAt(Timestamp.valueOf(LocalDateTime.now()));
        entityManager.persist(announcement);

        List<Announcement> announcements = announcementRepository.findByCourse(course);

        assertNotNull(announcements);
        assertEquals(1, announcements.size());
        assertEquals("Midterm Exam", announcements.get(0).getTitle());
    }

    @Test
    void testFindByCourse_NoAnnouncements() {
        Instructor instructor = new Instructor();
        instructor.setName("Instructor Name");
        instructor.setEmail("instructor@example.com");
        instructor.setPassword("securepassword");
        instructor.setRole(Role.INSTRUCTOR);
        entityManager.persist(instructor);

        Course course = new Course();
        course.setCode("CS102");
        course.setName("Data Structures");
        course.setDescription("A course on data structures.");
        course.setInstructor(instructor);
        entityManager.persist(course);

        List<Announcement> announcements = announcementRepository.findByCourse(course);

        assertNotNull(announcements);
        assertEquals(0, announcements.size());
    }

    @Test
    void testFindByCourse_MultipleAnnouncements() {
        Instructor instructor = new Instructor();
        instructor.setName("Instructor Name");
        instructor.setEmail("instructor@example.com");
        instructor.setPassword("securepassword");
        instructor.setRole(Role.INSTRUCTOR);
        entityManager.persist(instructor);

        Course course = new Course();
        course.setCode("CS103");
        course.setName("Algorithms");
        course.setDescription("A course on algorithms.");
        course.setInstructor(instructor);
        entityManager.persist(course);

        Announcement announcement1 = new Announcement();
        announcement1.setCourse(course);
        announcement1.setPostedBy(instructor);
        announcement1.setTitle("First Announcement");
        announcement1.setContent("Details about the first announcement...");
        announcement1.setPostedAt(Timestamp.valueOf(LocalDateTime.now()));
        entityManager.persist(announcement1);

        Announcement announcement2 = new Announcement();
        announcement2.setCourse(course);
        announcement2.setPostedBy(instructor);
        announcement2.setTitle("Second Announcement");
        announcement2.setContent("Details about the second announcement...");
        announcement2.setPostedAt(Timestamp.valueOf(LocalDateTime.now()));
        entityManager.persist(announcement2);

        List<Announcement> announcements = announcementRepository.findByCourse(course);

        assertNotNull(announcements);
        assertEquals(2, announcements.size());
        assertEquals("First Announcement", announcements.get(0).getTitle());
        assertEquals("Second Announcement", announcements.get(1).getTitle());
    }

}
