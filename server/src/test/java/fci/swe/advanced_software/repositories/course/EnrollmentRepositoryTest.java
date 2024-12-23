package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Enrollment;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.repositories.course.EnrollmentRepository;
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
        // Create Instructor and Course
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS101", "Introduction to Computer Science");

        // Create Students
        Student student1 = createStudent("student1@example.com");
        Student student2 = createStudent("student2@example.com");

        // Enroll Students in Course
        createEnrollment(student1, course);
        createEnrollment(student2, course);

        // Fetch Enrollments for the Course
        List<Enrollment> enrollments = enrollmentRepository.findByCourse(course);

        // Assertions
        assertNotNull(enrollments);
        assertEquals(2, enrollments.size());
        assertTrue(enrollments.stream().anyMatch(e -> e.getStudent().getEmail().equals("student1@example.com")));
        assertTrue(enrollments.stream().anyMatch(e -> e.getStudent().getEmail().equals("student2@example.com")));
    }

    @Test
    void testFindByCourse_NoEnrollments() {
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS102", "Data Structures");

        // Fetch Enrollments for the Course
        List<Enrollment> enrollments = enrollmentRepository.findByCourse(course);

        assertNotNull(enrollments);
        assertEquals(0, enrollments.size());
    }

    @Test
    void testFindAllByStudent() {
        // Create Instructor, Course, and Student
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS101", "Introduction to Computer Science");
        Student student = createStudent("student@example.com");

        // Enroll Student in Course
        createEnrollment(student, course);

        // Create Pageable
        Pageable pageable = repositoryUtils.getPageable(0, 10, Sort.Direction.ASC, "course.name");

        // Fetch Enrollments by Student
        Page<Enrollment> enrollmentsPage = enrollmentRepository.findAllByStudent(student, pageable);

        // Assertions
        assertNotNull(enrollmentsPage);
        assertEquals(1, enrollmentsPage.getTotalElements());
        assertTrue(enrollmentsPage.getContent().stream().anyMatch(e -> e.getCourse().getCode().equals("CS101")));
    }

    @Test
    void testFindByStudentAndCourse() {
        // Create Instructor, Course, and Student
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS101", "Introduction to Computer Science");
        Student student = createStudent("student@example.com");

        // Enroll Student in Course
        Enrollment enrollment = createEnrollment(student, course);

        // Fetch Enrollment by Student and Course
        Enrollment foundEnrollment = enrollmentRepository.findByStudentAndCourse(student, course);

        // Assertions
        assertNotNull(foundEnrollment);
        assertEquals(enrollment.getStudent().getEmail(), foundEnrollment.getStudent().getEmail());
        assertEquals(enrollment.getCourse().getCode(), foundEnrollment.getCourse().getCode());
    }

    @Test
    void testExistsByStudentIdAndCourseId() {
        // Create Instructor, Course, and Student
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS101", "Introduction to Computer Science");
        Student student = createStudent("student@example.com");

        // Enroll Student in Course
        createEnrollment(student, course);

        // Check if enrollment exists
        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId());

        // Assertions
        assertTrue(exists);
    }

    @Test
    void testExistsByStudentIdAndCourseId_NoEnrollment() {
        // Create Instructor and Course
        Instructor instructor = createInstructor();
        Course course = createCourse(instructor, "CS102", "Data Structures");

        // Create Student (but do not enroll)
        Student student = createStudent("student@example.com");

        // Check if enrollment exists
        boolean exists = enrollmentRepository.existsByStudentIdAndCourseId(student.getId(), course.getId());

        // Assertions
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
        student.setRole(Role.STUDENT);  // Ensure the role is set
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
