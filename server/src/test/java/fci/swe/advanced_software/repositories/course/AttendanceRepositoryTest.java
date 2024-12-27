package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.courses.Attendance;
import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.models.users.Student;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class AttendanceRepositoryTest {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private EntityManager entityManager;

    private Lesson createLesson(Course course, String title, String content, String otp) {
        Lesson lesson = new Lesson();
        lesson.setTitle(title);
        lesson.setContent(content);
        lesson.setOtp(otp);
        lesson.setCourse(course);
        entityManager.persist(lesson);
        return lesson;
    }

    @Test
    void testFindAllByStudent() {
        Student student = new Student();
        student.setName("Student Name");
        student.setEmail("student@example.com");
        student.setPassword("securepassword");
        student.setRole(Role.STUDENT);
        entityManager.persist(student);

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

        Lesson lesson = createLesson(course, "Lesson 1: Basics of Computer Science", "Introductory lesson content", "OTP123");

        Attendance attendance = new Attendance();
        attendance.setCourse(course);
        attendance.setStudent(student);
        attendance.setLesson(lesson);
        attendance.setAttendedAt(Timestamp.valueOf(LocalDateTime.now()));
        entityManager.persist(attendance);

        List<Attendance> attendances = attendanceRepository.findAllByStudent(student);

        assertNotNull(attendances);
        assertEquals(1, attendances.size());
        assertEquals(student.getName(), attendances.get(0).getStudent().getName());
        assertEquals(course.getName(), attendances.get(0).getCourse().getName());
        assertEquals(lesson.getTitle(), attendances.get(0).getLesson().getTitle());
    }

    @Test
    void testFindAllByStudent_NoAttendances() {
        Student student = new Student();
        student.setName("Student2 Name");
        student.setEmail("student2@example.com");
        student.setPassword("securepassword");
        student.setRole(Role.STUDENT);
        entityManager.persist(student);

        List<Attendance> attendances = attendanceRepository.findAllByStudent(student);

        assertNotNull(attendances);
        assertEquals(0, attendances.size());
    }

    @Test
    void testFindAllByStudent_MultipleAttendances() {
        Student student = new Student();
        student.setName("Student3 Name");
        student.setEmail("student3@example.com");
        student.setPassword("securepassword");
        student.setRole(Role.STUDENT);
        entityManager.persist(student);

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

        Lesson lesson1 = createLesson(course, "Lesson 1: Introduction to Data Structures", "Lesson content for data structures", "OTP124");
        Lesson lesson2 = createLesson(course, "Lesson 2: Arrays and Linked Lists", "Lesson content for arrays and linked lists", "OTP125");

        Attendance attendance1 = new Attendance();
        attendance1.setCourse(course);
        attendance1.setStudent(student);
        attendance1.setLesson(lesson1);
        attendance1.setAttendedAt(Timestamp.valueOf(LocalDateTime.now().minusDays(1)));
        entityManager.persist(attendance1);

        Attendance attendance2 = new Attendance();
        attendance2.setCourse(course);
        attendance2.setStudent(student);
        attendance2.setLesson(lesson2);
        attendance2.setAttendedAt(Timestamp.valueOf(LocalDateTime.now()));
        entityManager.persist(attendance2);

        List<Attendance> attendances = attendanceRepository.findAllByStudent(student);

        assertNotNull(attendances);
        assertEquals(2, attendances.size());
        assertEquals(student.getName(), attendances.get(0).getStudent().getName());
        assertEquals(course.getName(), attendances.get(0).getCourse().getName());
        assertEquals(lesson1.getTitle(), attendances.get(0).getLesson().getTitle());
        assertEquals(lesson2.getTitle(), attendances.get(1).getLesson().getTitle());
    }

    @Test
    void testFindAllByStudentAndCourse() {
        Student student = new Student();
        student.setName("Student Name");
        student.setEmail("student@example.com");
        student.setPassword("securepassword");
        student.setRole(Role.STUDENT);
        entityManager.persist(student);

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

        Lesson lesson = createLesson(course, "Lesson 1: Basics of Computer Science", "Introductory lesson content", "OTP123");

        Attendance attendance = new Attendance();
        attendance.setCourse(course);
        attendance.setStudent(student);
        attendance.setLesson(lesson);
        attendance.setAttendedAt(Timestamp.valueOf(LocalDateTime.now()));
        entityManager.persist(attendance);

        List<Attendance> attendances = attendanceRepository.findAllByStudentAndCourse(student, course);

        assertNotNull(attendances);
        assertEquals(1, attendances.size());
        assertEquals(student.getName(), attendances.get(0).getStudent().getName());
        assertEquals(course.getName(), attendances.get(0).getCourse().getName());
        assertEquals(lesson.getTitle(), attendances.get(0).getLesson().getTitle());
    }

    @Test
    void testExistsByLessonIdAndStudentId() {
        Student student = new Student();
        student.setName("Student Name");
        student.setEmail("student@example.com");
        student.setPassword("securepassword");
        student.setRole(Role.STUDENT);
        entityManager.persist(student);

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

        Lesson lesson = createLesson(course, "Lesson 1: Basics of Computer Science", "Introductory lesson content", "OTP123");

        Attendance attendance = new Attendance();
        attendance.setCourse(course);
        attendance.setStudent(student);
        attendance.setLesson(lesson);
        attendance.setAttendedAt(Timestamp.valueOf(LocalDateTime.now()));
        entityManager.persist(attendance);

        boolean exists = attendanceRepository.existsByLessonIdAndStudentId(lesson.getId(), student.getId());
        assertTrue(exists);
    }
}
