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
@ActiveProfiles("test") // Use the test profile if configured
class AnnouncementRepositoryTest {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindByCourse() {
        // Insert Instructor with required fields
        Instructor instructor = new Instructor();
        instructor.setName("Instructor Name");
        instructor.setEmail("instructor@example.com");
        instructor.setPassword("securepassword");
        instructor.setRole(Role.INSTRUCTOR);
        entityManager.persist(instructor);

        // Insert Course with required fields
        Course course = new Course();
        course.setCode("CS101");
        course.setName("Introduction to Computer Science");
        course.setDescription("This is a basic course in computer science."); // Set the description
        course.setInstructor(instructor); // Set the instructor
        entityManager.persist(course);

        // Insert Announcement
        Announcement announcement = new Announcement();
        announcement.setCourse(course);
        announcement.setPostedBy(instructor);
        announcement.setTitle("Midterm Exam");
        announcement.setContent("Details about the midterm exam...");
        announcement.setPostedAt(Timestamp.valueOf(LocalDateTime.now()));
        entityManager.persist(announcement);

        // Fetch data from the repository
        List<Announcement> announcements = announcementRepository.findByCourse(course);

        // Assert results
        assertNotNull(announcements);
        assertEquals(1, announcements.size());
        assertEquals("Midterm Exam", announcements.get(0).getTitle());
    }

}
