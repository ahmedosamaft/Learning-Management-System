package fci.swe.advanced_software.config;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.courses.Lesson;
import fci.swe.advanced_software.models.users.Admin;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.assessments.AssessmentRepository;
import fci.swe.advanced_software.repositories.auth.RoleRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.course.LessonRepository;
import fci.swe.advanced_software.repositories.users.AdminRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import fci.swe.advanced_software.repositories.users.StudentRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@AllArgsConstructor
public class DataLoader {
    private RoleRepository roleRepository;
    private CourseRepository courseRepository;
    private LessonRepository lessonRepository;
    private InstructorRepository instructorRepository;
    private AdminRepository adminRepository;
    private StudentRepository studentRepository;
    private AssessmentRepository assessmentRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadData() {
        loadRoles();
        loadAdmins();
        loadInstructors();
        loadStudents();
        loadCourses();
    }

    private void loadRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(Role.builder().name("STUDENT").build());
            roleRepository.save(Role.builder().name("INSTRUCTOR").build());
            roleRepository.save(Role.builder().name("ADMIN").build());
        }
    }

    private void loadAdmins() {
        if (!adminRepository.existsByEmail("abdelhady@fcai.admin.com")) {
            adminRepository.save(Admin.builder()
                    .name("Abdelhady")
                    .email("abdelhady@fcai.admin.com")
                    .password(passwordEncoder.encode("123456"))
                    .roles(Set.of(roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("NO ROLE FOUND"))))
                    .build());
        }
    }

    private void loadInstructors() {
        if (!instructorRepository.existsByEmail("ahmed@fcai.instructor.com")) {
            instructorRepository.save(Instructor.builder()
                    .name("Ahmed")
                    .email("ahmed@fcai.instructor.com")
                    .password(passwordEncoder.encode("123456"))
                    .roles(Set.of(roleRepository.findByName("INSTRUCTOR").orElseThrow(() -> new RuntimeException("NO ROLE FOUND"))))
                    .build());
        }

        if (!instructorRepository.existsByEmail("tamer@fcai.instructor.com")) {
            instructorRepository.save(Instructor.builder()
                    .name("Tamer")
                    .email("tamer@fcai.instructor.com")
                    .password(passwordEncoder.encode("123456"))
                    .roles(Set.of(roleRepository.findByName("INSTRUCTOR").orElseThrow(() -> new RuntimeException("NO ROLE FOUND"))))
                    .build());
        }
    }

    private void loadStudents() {
        if (!studentRepository.existsByEmail("pop@fcai.student.com")) {
            studentRepository.save(Student.builder()
                    .name("pop")
                    .email("pop@fcai.student.com")
                    .password(passwordEncoder.encode("123456"))
                    .roles(Set.of(roleRepository.findByName("STUDENT").orElseThrow(() -> new RuntimeException("NO ROLE FOUND"))))
                    .build());
        }

        if (!studentRepository.existsByEmail("sayed@fcai.student.com")) {
            studentRepository.save(Student.builder()
                    .name("sayed")
                    .email("sayed@fcai.student.com")
                    .password(passwordEncoder.encode("123456"))
                    .roles(Set.of(roleRepository.findByName("STUDENT").orElseThrow(() -> new RuntimeException("NO ROLE FOUND"))))
                    .build());
        }
    }

    private void loadCourses() {
        if (courseRepository.count() == 0) {
            Course softwareEngineering = courseRepository.save(Course.builder()
                    .code("CS321")
                    .instructor(
                            instructorRepository
                                    .findByEmail("ahmed@fcai.instructor.com").orElseThrow(() -> new RuntimeException("NO INSTRUCTOR FOUND"))
                    )
                    .name("Software Engineering")
                    .description("This course introduces students to the principles of software engineering.")
                    .build()
            );

            Course oop = courseRepository.save(Course.builder()
                    .code("CS311")
                    .instructor(
                            instructorRepository
                                    .findByEmail("tamer@fcai.instructor.com").orElseThrow(() -> new RuntimeException("NO INSTRUCTOR FOUND"))
                    )
                    .name("OOP")
                    .description("This course introduces students to the principles of OOP.")
                    .build()
            );

            loadLessonsToCourse(softwareEngineering);
            loadLessonsToCourse(oop);
        }
    }

    private void loadLessonsToCourse(Course course) {
        for (int i = 1; i <= 5; i++) {
            lessonRepository.save(Lesson.builder()
                    .title("Lesson " + i + " for " + course.getName())
                    .content("This is the content of Lesson " + i + " for the course " + course.getName() + ".")
                    .otp(course.getCode().toLowerCase() + i)
                    .course(course)
                    .build());
        }
    }
}
