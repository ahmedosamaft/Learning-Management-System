package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.models.users.Student;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class EnrollmentRepositoryTest {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

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
    void testFindByCourse() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS101", "Introduction to Computer Science");

        Student student1 = createStudent("student1@example.com");
        Student student2 = createStudent("student2@example.com");

        createEnrollment(student1, course);
        createEnrollment(student2, course);

        List<Enrollment> enrollments = enrollmentRepository.findAllByCourseId(course.getId());

        assertNotNull(enrollments);
        assertEquals(2, enrollments.size());
        assertTrue(enrollments.stream().anyMatch(e -> e.getStudent().getEmail().equals("student1@example.com")));
        assertTrue(enrollments.stream().anyMatch(e -> e.getStudent().getEmail().equals("student2@example.com")));
    }

    @Test
    void testFindByCourse_NoEnrollments() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS102", "Data Structures");

        List<Enrollment> enrollments = enrollmentRepository.findAllByCourseId(course.getId());

        assertNotNull(enrollments);
        assertEquals(0, enrollments.size());
    }

    @Test
    void testFindAllByStudent() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS101", "Introduction to Computer Science");
        Student student = createStudent("student@example.com");

        createEnrollment(student, course);

        Pageable pageable = repositoryUtils.getPageable(0, 10, Sort.Direction.ASC, "course.name");

        Page<Enrollment> enrollmentsPage = enrollmentRepository.findAllByStudent(student, pageable);

        assertNotNull(enrollmentsPage);
        assertEquals(1, enrollmentsPage.getTotalElements());
        assertTrue(enrollmentsPage.getContent().stream().anyMatch(e -> e.getCourse().getCode().equals("CS101")));
    }

    @Test
    void testFindByStudentAndCourse() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS101", "Introduction to Computer Science");
        Student student = createStudent("student@example.com");

        Enrollment enrollment = createEnrollment(student, course);

        Enrollment foundEnrollment = enrollmentRepository.findByStudentAndCourse(student, course);

        assertNotNull(foundEnrollment);
        assertEquals(enrollment.getStudent().getEmail(), foundEnrollment.getStudent().getEmail());
        assertEquals(enrollment.getCourse().getCode(), foundEnrollment.getCourse().getCode());
    }

    @Test
    void testExistsByStudentIdAndCourseId() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS101", "Introduction to Computer Science");
        Student student = createStudent("student@example.com");

        createEnrollment(student, course);

        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId());

        assertTrue(exists);
    }

    @Test
    void testExistsByStudentIdAndCourseId_NoEnrollment() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS102", "Data Structures");

        Student student = createStudent("student@example.com");

        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId());

        assertFalse(exists);
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

    private Student createStudent(String email) {
        Student student = new Student();
        student.setName("Student Name");
        student.setEmail(email);
        student.setPassword("studentpassword");
        student.setRole(Role.STUDENT);
        entityManager.persist(student);
        return student;
    }

    private Enrollment createEnrollment(Student student, Course course) {
        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        entityManager.persist(enrollment);
        return enrollment;
    }
}
