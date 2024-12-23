package fci.swe.advanced_software.repositories.course;

import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.repositories.users.InstructorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class InstructorRepositoryTests {

    @Autowired
    private InstructorRepository instructorRepository;

    @Test
    public void InstructorRepository_Save_ReturnsSavedInstructor() {
        Instructor instructor = Instructor.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .build();

        instructor = instructorRepository.save(instructor);

        assertThat(instructor).isNotNull();
        assertThat(instructor.getId()).isNotNull();
    }

    @Test
    public void InstructorRepository_FindById_ReturnsInstructor() {
        Instructor instructor = Instructor.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .build();

        instructor = instructorRepository.save(instructor);

        Instructor foundInstructor = instructorRepository.findById(instructor.getId()).orElse(null);

        assertThat(foundInstructor).isNotNull();
        assertThat(foundInstructor.getId()).isEqualTo(instructor.getId());
    }

    @Test
    public void InstructorRepository_FindById_ReturnsNull() {
        Instructor foundInstructor = instructorRepository.findById(String.valueOf(UUID.randomUUID())).orElse(null);

        assertThat(foundInstructor).isNull();
    }

    @Test
    public void InstructorRepository_FindAll_ReturnsInstructors() {
        Instructor instructor1 = Instructor.builder()
                .name("testA")
                .email("testA@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        Instructor instructor2 = Instructor.builder()
                .name("testB")
                .email("testB@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        instructorRepository.save(instructor1);
        instructorRepository.save(instructor2);

        Iterable<Instructor> instructors = instructorRepository.findAll();

        assertThat(instructors).hasSize(2);
        assertThat(instructors).contains(instructor1, instructor2);
    }

    @Test
    public void InstructorRepository_FindByEmail_ReturnsInstructor() {
        Instructor instructor = Instructor.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        instructor = instructorRepository.save(instructor);

        Instructor foundInstructor = instructorRepository.findByEmail(instructor.getEmail()).orElse(null);

        assertThat(foundInstructor).isNotNull();
        assertThat(foundInstructor.getId()).isEqualTo(instructor.getId());
    }

    @Test
    public void InstructorRepository_FindByEmail_ReturnsNull() {
        Instructor foundInstructor = instructorRepository.findByEmail("test@fci.swe.com").orElse(null);

        assertThat(foundInstructor).isNull();
    }

    @Test
    public void InstructorRepository_ExistsById_ReturnsTrue() {
        Instructor instructor = Instructor.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        instructorRepository.save(instructor);

        assertThat(instructorRepository.existsById(instructor.getId())).isTrue();
    }

    @Test
    public void InstructorRepository_ExistsById_ReturnsFalse() {
        assertThat(instructorRepository.existsById(String.valueOf(UUID.randomUUID()))).isFalse();
    }

    @Test
    public void InstructorRepository_ExistsByEmail_ReturnsTrue() {
        Instructor instructor = Instructor.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        instructorRepository.save(instructor);

        assertThat(instructorRepository.existsByEmail(instructor.getEmail())).isTrue();
    }

    @Test
    public void InstructorRepository_ExistsByEmail_ReturnsFalse() {
        assertThat(instructorRepository.existsByEmail("test@fci.swe.com")).isFalse();
    }

    @Test
    public void InstructorRepository_Delete_DeletesInstructor() {
        Instructor instructor = Instructor.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        instructor = instructorRepository.save(instructor);

        instructorRepository.delete(instructor);

        Instructor foundInstructor = instructorRepository.findById(instructor.getId()).orElse(null);

        assertThat(foundInstructor).isNull();
    }

    @Test
    public void InstructorRepository_DeleteById_DeletesInstructor() {
        Instructor instructor = Instructor.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.INSTRUCTOR)
                .build();

        instructor = instructorRepository.save(instructor);

        instructorRepository.deleteById(instructor.getId());

        Instructor foundInstructor = instructorRepository.findById(instructor.getId()).orElse(null);

        assertThat(foundInstructor).isNull();
    }
}