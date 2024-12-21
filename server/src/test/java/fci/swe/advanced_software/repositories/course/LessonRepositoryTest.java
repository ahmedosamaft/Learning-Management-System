package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.utils.RepositoryUtils;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class LessonRepositoryTest {

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RepositoryUtils repositoryUtils;

    @TestConfiguration
    static class TestConfig {
        @Bean
        public RepositoryUtils repositoryUtils() {
            return new RepositoryUtils();
        }
    }

    @Test
    void testFindAllByCourseId() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS101", "Introduction to Computer Science");
        Lesson lesson = createLesson(course, "Lesson 1", "Content for lesson 1", "otp123");

        Pageable pageable = repositoryUtils.getPageable(1, 10, Sort.Direction.ASC, "title");
        Page<Lesson> lessons = lessonRepository.findAllByCourseId(course.getId(), pageable);

        assertNotNull(lessons);
        assertEquals(1, lessons.getTotalElements());
        assertEquals("Lesson 1", lessons.getContent().get(0).getTitle());
    }

    @Test
    void testFindAllByCourseId_NoLessons() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS102", "Data Structures");

        Pageable pageable = repositoryUtils.getPageable(1, 10, Sort.Direction.ASC, "title");
        Page<Lesson> lessons = lessonRepository.findAllByCourseId(course.getId(), pageable);

        assertNotNull(lessons);
        assertEquals(0, lessons.getTotalElements());
    }

    @Test
    void testFindAllByCourseId_MultipleLessons() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS103", "Algorithms");
        createLesson(course, "Lesson 1", "Content for lesson 1", "otp123");
        createLesson(course, "Lesson 2", "Content for lesson 2", "otp124");

        Pageable pageable = repositoryUtils.getPageable(1, 10, Sort.Direction.ASC, "title");
        Page<Lesson> lessons = lessonRepository.findAllByCourseId(course.getId(), pageable);

        assertNotNull(lessons);
        assertEquals(2, lessons.getTotalElements());
        assertEquals("Lesson 1", lessons.getContent().get(0).getTitle());
        assertEquals("Lesson 2", lessons.getContent().get(1).getTitle());
    }

    @Test
    void testFindAllByCourseId_EmptyPage() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS104", "Advanced Algorithms");

        Pageable pageable = repositoryUtils.getPageable(2, 10, Sort.Direction.ASC, "title");
        Page<Lesson> lessons = lessonRepository.findAllByCourseId(course.getId(), pageable);

        assertNotNull(lessons);
        assertEquals(0, lessons.getTotalElements());
    }

    private Instructor createInstructor() {
        Instructor instructor = new Instructor();
        instructor.setName("Instructor Name");
        instructor.setEmail("instructor@example.com");
        instructor.setPassword("securepassword");
        instructor.setRole(Role.INSTRUCTOR);
        entityManager.persist(instructor);
        return instructor;
    }

    private Course createCourse(Instructor instructor, String code, String name) {
        Course course = new Course();
        course.setCode(code);
        course.setName(name);
        course.setDescription("Description for " + name);
        course.setInstructor(instructor);
        entityManager.persist(course);
        return course;
    }

    private Lesson createLesson(Course course, String title, String content, String otp) {
        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setContent(content);
        lesson.setOtp(otp);
        lesson.setCourse(course);
        entityManager.persist(lesson);
        return lesson;
    }
}
