package fci.swe.advanced_software.config;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.repositories.auth.RoleRepository;
import fci.swe.advanced_software.repositories.course.CourseRepository;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader {
    private RoleRepository roleRepository;
    private CourseRepository courseRepository;
    private InstructorRepository instructorRepository;

    @PostConstruct
    public void loadRoles() {
        if (roleRepository.count() == 0) {
            roleRepository.save(Role.builder().name("STUDENT").build());
            roleRepository.save(Role.builder().name("INSTRUCTOR").build());
            roleRepository.save(Role.builder().name("ADMIN").build());
        }
        if (!instructorRepository.existsByEmail("ahmed@fcai-instructor.com")) {
            instructorRepository.save(Instructor.builder().name("Ahmed").email("ahmed@fcai-instructor.com").password("123456").build());
        }

        if (courseRepository.count() == 0) {
            courseRepository.save(Course.builder()
                    .code("CS321")
                    .instructor(
                            instructorRepository
                                    .findByEmail("ahmed@fcai-instructor.com").get()
                    )
                    .name("Software Engineering")
                    .description("This course introduces students to the principles of software engineering.")
                    .build()
            );
        }
    }
}