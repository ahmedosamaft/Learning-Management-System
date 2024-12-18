package fci.swe.advanced_software.config;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Admin;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.models.users.Student;
import fci.swe.advanced_software.repositories.auth.RoleRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
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
    private InstructorRepository instructorRepository;
    private AdminRepository adminRepository;
    private StudentRepository studentRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(Role.builder().name("STUDENT").build());
            roleRepository.save(Role.builder().name("INSTRUCTOR").build());
            roleRepository.save(Role.builder().name("ADMIN").build());
        }
        if (!adminRepository.existsByEmail("abdelhady@fcai.admin.com")) {
            adminRepository.save(Admin.builder()
                    .name("abdelhady")
                    .email("abdelhady@fcai.admin.com")
                    .password(passwordEncoder.encode("123456"))
                    .roles(Set.of(roleRepository.findByName("ADMIN").orElseThrow(() -> new RuntimeException("NO RULE FOUND"))))
                    .build());
        }

        if (!instructorRepository.existsByEmail("ahmed@fcai.instructor.com")) {
            instructorRepository.save(Instructor.builder()
                    .name("Ahmed")
                    .email("ahmed@fcai.instructor.com")
                    .password(passwordEncoder.encode("123456"))
                    .roles(Set.of(roleRepository.findByName("INSTRUCTOR").orElseThrow(() -> new RuntimeException("NO RULE FOUND"))))
                    .build());
        }

        if (!studentRepository.existsByEmail("pop@fcai.student.com")) {
            studentRepository.save(Student.builder()
                    .name("pop")
                    .email("pop@fcai.student.com")
                    .password(passwordEncoder.encode("123456"))
                    .roles(Set.of(roleRepository.findByName("STUDENT").orElseThrow(() -> new RuntimeException("NO RULE FOUND"))))
                    .build());
        }

        if (courseRepository.count() == 0) {
            courseRepository.save(Course.builder()
                    .code("CS321")
                    .instructor(
                            instructorRepository
                                    .findByEmail("ahmed@fcai.instructor.com").orElseThrow(() -> new RuntimeException("NO INSTRUCTOR FOUND"))
                    )
                    .name("Software Engineering")
                    .description("This course introduces students to the principles of software engineering.")
                    .build()
            );
        }
    }
}