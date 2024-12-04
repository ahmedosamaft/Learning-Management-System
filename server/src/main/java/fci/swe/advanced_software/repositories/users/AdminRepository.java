package fci.swe.advanced_software.repositories.users;

import fci.swe.advanced_software.models.courses.Course;
import fci.swe.advanced_software.models.users.Admin;
import fci.swe.advanced_software.models.users.Instructor;
import fci.swe.advanced_software.models.users.Student;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdminRepository extends AbstractUserRepository<Admin> {
    List<Course> findAllCourses();
    Course findCourseById(@Param("id") Long id);
    Course saveCourse(Course course);
    void deleteCourseById(@Param("id") Long id);

    List<Student> findAllStudents();
    Student findStudentById(@Param("id") Long id);
    Student saveStudent(Student student);
    void deleteStudentById(@Param("id") Long id);

    List<Instructor> findAllInstructors();
    Instructor findInstructorById(@Param("id") Long id);
    Instructor saveInstructor(Instructor instructor);
    void deleteInstructorById(@Param("id") Long id);
}
