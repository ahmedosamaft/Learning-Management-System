package fci.swe.advanced_software.repositories.users;

import fci.swe.advanced_software.models.users.Role;
import fci.swe.advanced_software.models.users.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class StudentRepositoryTests {

    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void StudentRepository_Save_ReturnsSavedStudent() {
        Student student = Student.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        student = studentRepository.save(student);

        assertThat(student).isNotNull();
        assertThat(student.getId()).isNotNull();
    }

    @Test
    public void StudentRepository_FindById_ReturnsStudent() {
        Student student = Student.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        student = studentRepository.save(student);

        Student foundStudent = studentRepository.findById(student.getId()).orElse(null);

        assertThat(foundStudent).isNotNull();
        assertThat(foundStudent.getId()).isEqualTo(student.getId());
    }

    @Test
    public void StudentRepository_FindById_ReturnsNull() {
        Student foundStudent = studentRepository.findById(String.valueOf(UUID.randomUUID())).orElse(null);

        assertThat(foundStudent).isNull();
    }

    @Test
    public void StudentRepository_FindAll_ReturnsStudents() {
        Student student1 = Student.builder()
                .name("testA")
                .email("testA@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        Student student2 = Student.builder()
                .name("testB")
                .email("testB@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        studentRepository.save(student1);
        studentRepository.save(student2);

        Iterable<Student> students = studentRepository.findAll();

        assertThat(students).hasSize(2);
        assertThat(students).contains(student1, student2);
    }

    @Test
    public void StudentRepository_FindByEmail_ReturnsStudent() {
        Student student = Student.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        student = studentRepository.save(student);

        Student foundStudent = studentRepository.findByEmail(student.getEmail()).orElse(null);

        assertThat(foundStudent).isNotNull();
        assertThat(foundStudent.getId()).isEqualTo(student.getId());
    }

    @Test
    public void StudentRepository_FindByEmail_ReturnsNull() {
        Student foundStudent = studentRepository.findByEmail("test@fci.swe.com").orElse(null);

        assertThat(foundStudent).isNull();
    }

    @Test
    public void StudentRepository_ExistsById_ReturnsTrue() {
        Student student = Student.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        studentRepository.save(student);

        assertThat(studentRepository.existsById(student.getId())).isTrue();
    }

    @Test
    public void StudentRepository_ExistsById_ReturnsFalse() {
        assertThat(studentRepository.existsById(String.valueOf(UUID.randomUUID()))).isFalse();
    }

    @Test
    public void StudentRepository_ExistsByEmail_ReturnsTrue() {
        Student student = Student.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        studentRepository.save(student);

        assertThat(studentRepository.existsByEmail(student.getEmail())).isTrue();
    }

    @Test
    public void StudentRepository_ExistsByEmail_ReturnsFalse() {
        assertThat(studentRepository.existsByEmail("test@fci.swe.com")).isFalse();
    }

    @Test
    public void StudentRepository_Delete_DeletesStudent() {
        Student student = Student.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        student = studentRepository.save(student);

        studentRepository.delete(student);

        Student foundStudent = studentRepository.findById(student.getId()).orElse(null);

        assertThat(foundStudent).isNull();
    }

    @Test
    public void StudentRepository_DeleteById_DeletesStudent() {
        Student student = Student.builder()
                .name("test")
                .email("test@fci.swe.com")
                .password("123456")
                .role(Role.STUDENT)
                .build();

        student = studentRepository.save(student);

        studentRepository.deleteById(student.getId());

        Student foundStudent = studentRepository.findById(student.getId()).orElse(null);

        assertThat(foundStudent).isNull();
    }
}
