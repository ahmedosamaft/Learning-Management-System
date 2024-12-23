package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void testFindAllByInstructorId() {
        Instructor instructor = new Instructor();
        instructor.setName("Instructor Name");
        instructor.setEmail("instructor@example.com");
        instructor.setPassword("securepassword");
        instructor.setRole(Role.INSTRUCTOR);
        entityManager.persist(instructor);

        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        course1.setDescription("This is a basic course in computer science.");
        course1.setInstructor(instructor);
        entityManager.persist(course1);

        Course course2 = new Course();
        course2.setCode("CS102");
        course2.setName("Data Structures");
        course2.setDescription("This is a course on data structures.");
        course2.setInstructor(instructor);
        entityManager.persist(course2);

        Page<Course> coursesPage = courseRepository.findAllByInstructorId(instructor.getId(), PageRequest.of(0, 10));

        assertNotNull(coursesPage);
        assertEquals(2, coursesPage.getTotalElements());
    }

    @Test
    void testExistsByInstructorId() {
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

        boolean exists = courseRepository.existsByInstructorId(instructor.getId());
        assertTrue(exists);
    }

    @Test
    void testExistsByIdAndInstructorId() {
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

        boolean exists = courseRepository.existsByIdAndInstructorId(course.getId(), instructor.getId());
        assertTrue(exists);
    }

    @Test
    void testFindAllByNameContainingIgnoreCase() {
        Instructor instructor = new Instructor();
        instructor.setName("Instructor Name");
        instructor.setEmail("instructor@example.com");
        instructor.setPassword("securepassword");
        instructor.setRole(Role.INSTRUCTOR);
        entityManager.persist(instructor);

        Course course1 = new Course();
        course1.setCode("CS101");
        course1.setName("Introduction to Computer Science");
        course1.setDescription("This is a basic course in computer science.");
        course1.setInstructor(instructor);
        entityManager.persist(course1);

        Course course2 = new Course();
        course2.setCode("CS102");
        course2.setName("Advanced Data Structures");
        course2.setDescription("This is a course on advanced data structures.");
        course2.setInstructor(instructor);
        entityManager.persist(course2);

        Page<Course> coursesPage = courseRepository.findAllByNameContainingIgnoreCase("Data", PageRequest.of(0, 10));

        assertNotNull(coursesPage);
        assertEquals(1, coursesPage.getTotalElements());
        assertEquals("Advanced Data Structures", coursesPage.getContent().get(0).getName());
    }

    @Test
    void testFindAllByInstructorId_Empty() {
        Instructor instructor = new Instructor();
        instructor.setName("Instructor Name");
        instructor.setEmail("instructor@example.com");
        instructor.setPassword("securepassword");
        instructor.setRole(Role.INSTRUCTOR);
        entityManager.persist(instructor);

        Page<Course> coursesPage = courseRepository.findAllByInstructorId(instructor.getId(), PageRequest.of(0, 10));

        assertNotNull(coursesPage);
        assertEquals(0, coursesPage.getTotalElements());
    }
}
