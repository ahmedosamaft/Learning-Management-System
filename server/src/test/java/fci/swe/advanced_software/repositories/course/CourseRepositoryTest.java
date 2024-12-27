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

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    void CourseRepository_FindAllByInstructorId_ReturnsCourses() {
        Instructor instructor = Instructor.builder()
                .name("Instructor Name")
                .email("instructor@example.com")
                .password("securepassword")
                .role(Role.INSTRUCTOR)
                .build();
        entityManager.persist(instructor);

        Course course1 = Course.builder()
                .code("CS101")
                .name("Introduction to Computer Science")
                .description("This is a basic course in computer science.")
                .instructor(instructor)
                .build();
        entityManager.persist(course1);

        Course course2 = Course.builder()
                .code("CS102")
                .name("Data Structures")
                .description("This is a course on data structures.")
                .instructor(instructor)
                .build();
        entityManager.persist(course2);

        Page<Course> coursesPage = courseRepository.findAllByInstructorId(instructor.getId(), PageRequest.of(0, 10));

        assertThat(coursesPage).isNotNull();
        assertThat(coursesPage.getTotalElements()).isEqualTo(2);
    }

    @Test
    void CourseRepository_ExistsByInstructorId_ReturnsTrue() {
        Instructor instructor = Instructor.builder()
                .name("Instructor Name")
                .email("instructor@example.com")
                .password("securepassword")
                .role(Role.INSTRUCTOR)
                .build();
        entityManager.persist(instructor);

        Course course = Course.builder()
                .code("CS101")
                .name("Introduction to Computer Science")
                .description("This is a basic course in computer science.")
                .instructor(instructor)
                .build();
        entityManager.persist(course);

        boolean exists = courseRepository.existsByInstructorId(instructor.getId());
        assertThat(exists).isTrue();
    }

    @Test
    void CourseRepository_ExistsByIdAndInstructorId_ReturnsTrue() {
        Instructor instructor = Instructor.builder()
                .name("Instructor Name")
                .email("instructor@example.com")
                .password("securepassword")
                .role(Role.INSTRUCTOR)
                .build();
        entityManager.persist(instructor);

        Course course = Course.builder()
                .code("CS101")
                .name("Introduction to Computer Science")
                .description("This is a basic course in computer science.")
                .instructor(instructor)
                .build();
        entityManager.persist(course);

        boolean exists = courseRepository.existsByIdAndInstructorId(course.getId(), instructor.getId());
        assertThat(exists).isTrue();
    }


    @Test
    void CourseRepository_FindAllByNameContainingIgnoreCase_ReturnsCourses() {
        Instructor instructor = Instructor.builder()
                .name("Instructor Name")
                .email("instructor@example.com")
                .password("securepassword")
                .role(Role.INSTRUCTOR)
                .build();
        entityManager.persist(instructor);

        Course course1 = Course.builder()
                .code("CS101")
                .name("Introduction to Computer Science")
                .description("This is a basic course in computer science.")
                .instructor(instructor)
                .build();
        entityManager.persist(course1);

        Course course2 = Course.builder()
                .code("CS102")
                .name("Advanced Data Structures")
                .description("This is a course on advanced data structures.")
                .instructor(instructor)
                .build();
        entityManager.persist(course2);

        Page<Course> coursesPage = courseRepository.findAllByNameContainingIgnoreCase("Data", PageRequest.of(0, 10));

        assertThat(coursesPage).isNotNull();
        assertThat(coursesPage.getTotalElements()).isEqualTo(1);
        assertThat(coursesPage.getContent().get(0).getName()).isEqualTo("Advanced Data Structures");
    }


    @Test
    void CourseRepository_FindAllByInstructorId_Empty_ReturnsEmptyPage() {
        Instructor instructor = Instructor.builder()
                .name("Instructor Name")
                .email("instructor@example.com")
                .password("securepassword")
                .role(Role.INSTRUCTOR)
                .build();
        entityManager.persist(instructor);

        Page<Course> coursesPage = courseRepository.findAllByInstructorId(instructor.getId(), PageRequest.of(0, 10));

        assertThat(coursesPage).isNotNull();
        assertThat(coursesPage.getTotalElements()).isEqualTo(0);
    }

    // Add more tests for other edge cases/scenarios as needed
}